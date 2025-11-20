package pl.edu.pg.eti.kask.app.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.app.recipe.model.function.*;
import pl.edu.pg.eti.kask.app.user.model.function.UserToModelFunction;
import pl.edu.pg.eti.kask.app.user.model.function.UsersToModelFunction;

@ApplicationScoped
public class ModelFunctionFactory {

    public RecipeToModelFunction recipeToModel() {
        return new RecipeToModelFunction();
    }

    public RecipesToModelFunction recipesToModel() {
        return new RecipesToModelFunction();
    }

    public RecipeToEditModelFunction recipeToEditModel() {
        return new RecipeToEditModelFunction(userToModel());
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

    public UserToModelFunction userToModel() {
        return new UserToModelFunction();
    }

    public UsersToModelFunction usersToModel() {
        return new UsersToModelFunction();
    }

    public ModelToCategoryFunction modelToCategory() {
        return new ModelToCategoryFunction();
    }

    public CategoryToEditModelFunction categoryToEditModel() {
        return new CategoryToEditModelFunction();
    }

    public UpdateCategoryWithModelFunction updateCategory() {
        return new UpdateCategoryWithModelFunction();
    }
}
