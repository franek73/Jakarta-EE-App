package pl.edu.pg.eti.kask.app.recipe.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.controller.api.RecipeController;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipeResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipesResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.PatchRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.dto.PutRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;

import java.util.UUID;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.logging.Level;

@Path("")
@Log
public class RecipeRestController implements RecipeController {

    private final RecipeService recipeService;

    private final CategoryService categoryService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public RecipeRestController(RecipeService recipeService, DtoFunctionFactory factory,
                                CategoryService categoryService,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.recipeService = recipeService;
        this.factory = factory;
        this.uriInfo = uriInfo;
        this.categoryService = categoryService;
    }

    @Override
    public GetRecipesResponse getRecipes() {
        return factory.recipesToResponse().apply(recipeService.findAll());
    }

    @Override
    public GetRecipesResponse getCategoryRecipes(UUID id) {
        return recipeService.findAllByCategory(id)
                .map(factory.recipesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRecipesResponse getUserRecipes(UUID id) {
        return recipeService.findAllByUser(id)
                .map(factory.recipesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRecipeResponse getRecipe(UUID id, UUID categoryId) {
        if (categoryService.find(categoryId).isPresent()) {
            if (recipeService.find(id).isPresent()) {
                var recipe = recipeService.find(id).map(factory.recipeToResponse()).get();
                if (recipe.getCategory().getId().equals(categoryId)) {
                    return recipe;
                }
                else {
                    throw new NotFoundException();
                }
            }
            else {
                throw new NotFoundException();
            }
        }
        else {
            throw new NotFoundException();
        }
    }


    @Override
    @SneakyThrows
    public void putRecipe(UUID id, UUID categoryId, PutRecipeRequest request) {
        categoryService.find(categoryId).ifPresentOrElse(
                category -> {
                    try {
                        request.setCategory(categoryId);
                        recipeService.create(factory.requestToRecipe().apply(id, request));

                        response.setHeader("Location", uriInfo.getBaseUriBuilder()
                                .path(RecipeController.class, "getRecipe")
                                .build(categoryId, id)
                                .toString());

                        throw new WebApplicationException(Response.Status.CREATED);
                    } catch (TransactionalException ex) {
                        if (ex.getCause() instanceof IllegalArgumentException) {
                            log.log(Level.WARNING, ex.getMessage(), ex);
                            throw new BadRequestException(ex);
                        }
                        throw ex;
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );

    }

    @Override
    public void patchRecipe(UUID id, UUID categoryId, PatchRecipeRequest request) {
        categoryService.find(categoryId).ifPresentOrElse(
                category -> recipeService.find(id).ifPresentOrElse(
                        recipe -> {
                            if (recipe.getCategory().getId().equals(categoryId)) {
                                recipeService.update(factory.updateRecipe().apply(recipe, request));
                            } else {
                                throw new NotFoundException();
                            }
                        },
                        () -> { throw new NotFoundException(); }
                ),
                () -> { throw new NotFoundException(); }
        );

    }

    @Override
    public void deleteRecipe(UUID id, UUID categoryId) {
        categoryService.find(categoryId).ifPresentOrElse(
                category -> recipeService.find(id).ifPresentOrElse(
                        recipe -> {
                            if (recipe.getCategory().getId().equals(categoryId)) {
                                recipeService.delete(id);
                            } else {
                                throw new NotFoundException();
                            }
                        },
                        () -> { throw new NotFoundException(); }
                ),
                () -> { throw new NotFoundException(); }
        );

    }

}
