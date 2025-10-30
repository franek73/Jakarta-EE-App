package pl.edu.pg.eti.kask.app.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.app.recipe.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public RecipeToModelFunction recipeToModel() {
        return new RecipeToModelFunction();
    }

    public RecipesToModelFunction recipesToModel() {
        return new RecipesToModelFunction();
    }

    public RecipeToEditModelFunction recipeToEditModel() {
        return new RecipeToEditModelFunction();
    }

    public ModelToRecipeFunction modelToRecipe() {
        return new ModelToRecipeFunction();
    }

    public UpdateRecipeWithModelFunction updateRecipe() {
        return new UpdateRecipeWithModelFunction();
    }

    public CategoryToModelFunction categoryToModel() {
        return new CategoryToModelFunction(recipeToModel());
    }

    public CategoriesToModelFunction categoriesToModel() {
        return new CategoriesToModelFunction();
    }
}
