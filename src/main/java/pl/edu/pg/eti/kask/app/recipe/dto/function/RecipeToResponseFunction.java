package pl.edu.pg.eti.kask.app.recipe.dto.function;

import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipeResponse;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;

import java.util.function.Function;

public class RecipeToResponseFunction implements Function<Recipe, GetRecipeResponse> {

    @Override
    public GetRecipeResponse apply(Recipe entity) {
        return GetRecipeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .difficulty(entity.getDifficulty())
                .creationDate(entity.getCreationDate())
                .category(GetRecipeResponse.Category.builder()
                        .id(entity.getCategory().getId())
                        .name(entity.getCategory().getName())
                        .build())
                .build();
    }
}
