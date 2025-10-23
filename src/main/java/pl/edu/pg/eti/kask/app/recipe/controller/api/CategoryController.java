package pl.edu.pg.eti.kask.app.recipe.controller.api;

import pl.edu.pg.eti.kask.app.recipe.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.app.recipe.dto.GetCategoryResponse;

import java.util.UUID;

public interface CategoryController {

    GetCategoriesResponse getCategories();

    GetCategoryResponse getCategory(UUID id);
}
