package pl.edu.pg.eti.kask.app.category.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.category.model.CategoryModel;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeModel;
import pl.edu.pg.eti.kask.app.category.service.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.model.RecipesModel;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class CategoryView implements Serializable {

    private RecipeService recipeService;

    private CategoryService categoryService;

    private final ModelFunctionFactory factory;

    private RecipesModel recipes;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private CategoryModel category;

    @Inject
    public CategoryView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @EJB
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public void init() throws IOException {
        Optional<Category> category = categoryService.find(id);
        if (category.isPresent()) {
            this.category = factory.categoryToModel().apply(category.get());

        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Category not found");
        }
    }

    public RecipesModel getRecipes() {
        if (recipes == null) {
            recipes = factory.recipesToModel().apply(recipeService.findAllForCallerPrincipalByCategory(category.getId()));
        }
        return recipes;
    }

    public void deleteRecipeAction(UUID id) {
        recipeService.deleteForCallerPrincipal(id);
        recipes = null;
    }

}
