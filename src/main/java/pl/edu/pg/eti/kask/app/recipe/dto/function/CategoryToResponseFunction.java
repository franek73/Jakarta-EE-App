package pl.edu.pg.eti.kask.app.recipe.dto.function;

import pl.edu.pg.eti.kask.app.recipe.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;

import java.util.function.Function;

public class CategoryToResponseFunction implements Function<Category, GetCategoryResponse> {

    @Override
    public GetCategoryResponse apply(Category entity) {
        return GetCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .recipes(
                        entity.getRecipes().stream()
                                .map(recipeEntity -> GetCategoryResponse.Recipe.builder()
                                        .id(recipeEntity.getId())
                                        .name(recipeEntity.getName())
                                        .creationDate(recipeEntity.getCreationDate())
                                        .description(recipeEntity.getDescription())
                                        .difficulty(recipeEntity.getDifficulty())
                                        .build())
                                .toList()
                )
                .build();
    }

}
