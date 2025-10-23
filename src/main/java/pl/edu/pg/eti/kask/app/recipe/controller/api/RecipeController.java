package pl.edu.pg.eti.kask.app.recipe.controller.api;

import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipeResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipesResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.PatchRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.dto.PutRecipeRequest;

import java.util.UUID;

public interface RecipeController {

    GetRecipesResponse getRecipes();

    GetRecipesResponse getCategoryRecipes(UUID id);

    GetRecipesResponse getUserRecipes(UUID id);

    GetRecipeResponse getRecipe(UUID id);

    void putRecipe(UUID id, PutRecipeRequest request);

    void patchRecipe(UUID id, PatchRecipeRequest request);

    void deleteRecipe(UUID id);

}
