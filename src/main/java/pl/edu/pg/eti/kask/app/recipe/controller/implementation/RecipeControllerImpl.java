package pl.edu.pg.eti.kask.app.recipe.controller.implementation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.controller.api.RecipeController;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipeResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipesResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.PatchRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.dto.PutRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;
import pl.edu.pg.eti.kask.app.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.app.controller.servlet.exception.NotFoundException;

import java.util.UUID;

@RequestScoped
public class RecipeControllerImpl implements RecipeController {

    private final RecipeService service;

    private final DtoFunctionFactory factory;

    @Inject
    public RecipeControllerImpl(RecipeService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetRecipesResponse getRecipes() {
        return factory.recipesToResponse().apply(service.findAll());
    }

    @Override
    public GetRecipesResponse getCategoryRecipes(UUID id) {
        return service.findAllByCategory(id)
                .map(factory.recipesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRecipesResponse getUserRecipes(UUID id) {
        return service.findAllByUser(id)
                .map(factory.recipesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetRecipeResponse getRecipe(UUID id) {
        return service.find(id)
                .map(factory.recipeToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putRecipe(UUID id, PutRecipeRequest request) {
        try {
            service.create(factory.requestToRecipe().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchRecipe(UUID id, PatchRecipeRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateRecipe().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteRecipe(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
