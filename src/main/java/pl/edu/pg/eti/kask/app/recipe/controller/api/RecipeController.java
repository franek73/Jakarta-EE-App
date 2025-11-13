package pl.edu.pg.eti.kask.app.recipe.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipeResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipesResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.PatchRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.dto.PutRecipeRequest;

import java.util.UUID;

@Path("")
public interface RecipeController {

    @GET
    @Path("/recipes")
    @Produces(MediaType.APPLICATION_JSON)
    GetRecipesResponse getRecipes();

    @GET
    @Path("/categories/{id}/recipes")
    @Produces(MediaType.APPLICATION_JSON)
    GetRecipesResponse getCategoryRecipes(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/recipes/")
    @Produces(MediaType.APPLICATION_JSON)
    GetRecipesResponse getUserRecipes(@PathParam("id") UUID id);

    @GET
    @Path("/categories/{categoryId}/recipes/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetRecipeResponse getRecipe(@PathParam("id") UUID id, @PathParam("categoryId") UUID categoryId);

    @PUT
    @Path("/categories/{categoryId}/recipes/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putRecipe(@PathParam("id") UUID id, @PathParam("categoryId") UUID categoryId, PutRecipeRequest request);

    @PATCH
    @Path("/categories/{categoryId}/recipes/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchRecipe(@PathParam("id") UUID id, @PathParam("categoryId") UUID categoryId, PatchRecipeRequest request);

    @DELETE
    @Path("/categories/{categoryId}/recipes/{id}")
    void deleteRecipe(@PathParam("id") UUID id, @PathParam("categoryId") UUID categoryId);

}
