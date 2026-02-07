package pl.edu.pg.eti.kask.app.category.dto.function;

import pl.edu.pg.eti.kask.app.category.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.app.category.entity.Category;

import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;

public class CategoryToResponseFunction implements Function<Category, GetCategoryResponse> {

    @Override
    public GetCategoryResponse apply(Category entity) {
        return GetCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .recipes(
                        Optional.ofNullable(entity.getRecipes())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(recipeEntity -> GetCategoryResponse.Recipe.builder()
                                        .id(recipeEntity.getId())
                                        .name(recipeEntity.getName())
                                        .build())
                                .toList()
                )
                .build();
    }

}
