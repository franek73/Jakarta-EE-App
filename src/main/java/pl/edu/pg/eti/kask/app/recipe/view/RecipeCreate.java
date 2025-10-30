package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryModel;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeCreateModel;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class RecipeCreate implements Serializable {

    private final RecipeService recipeService;

    private final CategoryService categoryService;

    private final ModelFunctionFactory factory;

    @Getter
    private RecipeCreateModel recipe;

    @Getter
    private List<CategoryModel> categories;

    @Getter
    private Difficulty[] difficulties;

    private final Conversation conversation;

    @Inject public RecipeCreate(RecipeService recipeService, CategoryService categoryService, ModelFunctionFactory factory, Conversation conversation) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
        this.factory = factory;
        this.conversation = conversation;
    }

    public void init() {
        if (conversation.isTransient()) {
            categories = categoryService.findAll().stream()
                    .map(factory.categoryToModel())
                    .collect(Collectors.toList());
            recipe = RecipeCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            difficulties = Difficulty.values();
            conversation.begin();
        }
    }

    public String goToCategoryAction() {
        return "/recipe/recipe_create__category.xhtml?faces-redirect=true";
    }

    public Object goToBasicAction() {
        return "/recipe/recipe_create__basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/recipe/recipe_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/recipe/recipe_create__confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        recipeService.create(factory.modelToRecipe().apply(recipe));
        conversation.end();
        return "/recipe/recipe_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }

}
