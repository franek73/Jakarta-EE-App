package pl.edu.pg.eti.kask.app.component;

import jakarta.enterprise.context.ApplicationScoped;
import pl.edu.pg.eti.kask.app.recipe.dto.function.*;
import pl.edu.pg.eti.kask.app.user.dto.function.*;

@ApplicationScoped
public class DtoFunctionFactory {

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    public UpdateUserWithRequestFunction updateUser() {
        return new UpdateUserWithRequestFunction();
    }

    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }

    public RecipeToResponseFunction recipeToResponse() {
        return new RecipeToResponseFunction();
    }

    public RecipesToResponseFunction recipesToResponse() {
        return new RecipesToResponseFunction();
    }

    public RequestToRecipeFunction requestToRecipe() {
        return new RequestToRecipeFunction();
    }

    public UpdateRecipeWithRequestFunction updateRecipe() {
        return new UpdateRecipeWithRequestFunction();
    }

    public CategoryToResponseFunction categoryToResponse() {
        return new CategoryToResponseFunction();
    }

    public CategoriesToResponseFunction categoriesToResponse() {
        return new CategoriesToResponseFunction();
    }

    public RequestToCategoryFunction requestToCategory() {
        return new RequestToCategoryFunction();
    }

    public UpdateCategoryWithRequestFunction updateCategory() {
        return new UpdateCategoryWithRequestFunction();
    }
}
