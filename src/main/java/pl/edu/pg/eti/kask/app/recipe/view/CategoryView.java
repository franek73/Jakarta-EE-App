package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryModel;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeModel;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
@Named
public class CategoryView implements Serializable {

    private final RecipeService recipeService;

    private final CategoryService categoryService;

    private final ModelFunctionFactory factory;

    @Getter
    private List<RecipeModel> recipes;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private CategoryModel category;

    @Inject
    public CategoryView(CategoryService categoryService, RecipeService recipeService, ModelFunctionFactory factory) {
        this.categoryService = categoryService;
        this.factory = factory;
        this.recipeService = recipeService;
    }

    public void init() throws IOException {
        Optional<Category> category = categoryService.find(id);
        if (category.isPresent()) {
            this.category = factory.categoryToModel().apply(category.get());

            this.recipes = recipeService.findAllByCategory(id).stream()
                    .flatMap(List::stream)
                    .map(factory.recipeToModel())
                    .collect(Collectors.toList());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public String deleteRecipeAction(UUID id) {
        recipeService.delete(id);
        return "category_view?faces-redirect=true&amp;id" + id;
    }

}
