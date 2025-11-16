package pl.edu.pg.eti.kask.app.recipe.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.*;
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
import pl.edu.pg.eti.kask.app.recipe.service.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.util.UUID;

import pl.edu.pg.eti.kask.app.user.entity.UserRole;

import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(UserRole.USER)
public class RecipeRestController implements RecipeController {

    private RecipeService recipeService;

    private CategoryService categoryService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public RecipeRestController(DtoFunctionFactory factory,
                                @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
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
                var recipe = recipeService.find(id).map(factory.recipeToResponse()).orElseThrow();
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
                    } catch (EJBException ex) {
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
                                try {
                                    recipeService.update(factory.updateRecipe().apply(recipe, request));
                                } catch (EJBException ex) {
                                    log.log(Level.WARNING, ex.getMessage(), ex);
                                    throw new ForbiddenException(ex);
                                }
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
                                try {
                                    recipeService.delete(id);
                                } catch (EJBException ex) {
                                    log.log(Level.WARNING, ex.getMessage(), ex);
                                    throw new ForbiddenException(ex);
                                }
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