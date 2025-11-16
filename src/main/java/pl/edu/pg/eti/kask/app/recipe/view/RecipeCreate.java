package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.ejb.EJB;
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
import pl.edu.pg.eti.kask.app.recipe.service.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class RecipeCreate implements Serializable {

    private RecipeService recipeService;

    private CategoryService categoryService;

    private ModelFunctionFactory factory;

    @Getter
    private RecipeCreateModel recipe;

    @Getter
    private List<CategoryModel> categories;

    @Getter
    private Difficulty[] difficulties;

    private final Conversation conversation;

    @Inject public RecipeCreate(ModelFunctionFactory factory, Conversation conversation) {
        this.factory = factory;
        this.conversation = conversation;
    }

    @EJB
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
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
        recipeService.createForCallerPrincipal(factory.modelToRecipe().apply(recipe));
        conversation.end();
        return "/recipe/recipe_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }

}
