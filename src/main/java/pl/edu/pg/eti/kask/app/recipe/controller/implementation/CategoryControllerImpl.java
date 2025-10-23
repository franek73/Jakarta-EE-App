package pl.edu.pg.eti.kask.app.recipe.controller.implementation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.controller.api.CategoryController;
import pl.edu.pg.eti.kask.app.recipe.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.app.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;

import java.util.UUID;

@RequestScoped
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService service;

    private final DtoFunctionFactory factory;

    @Inject
    public CategoryControllerImpl(CategoryService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetCategoriesResponse getCategories() {
        return factory.categoriesToResponse().apply(service.findAll());
    }

    @Override
    public GetCategoryResponse getCategory(UUID id) {
        return service.find(id)
                .map(factory.categoryToResponse())
                .orElseThrow(NotFoundException::new);
    }

}
