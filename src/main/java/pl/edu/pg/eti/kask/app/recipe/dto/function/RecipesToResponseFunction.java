package pl.edu.pg.eti.kask.app.recipe.dto.function;

import pl.edu.pg.eti.kask.app.recipe.dto.GetRecipesResponse;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;

import java.util.List;
import java.util.function.Function;

public class RecipesToResponseFunction implements Function<List<Recipe>, GetRecipesResponse> {

    @Override
    public GetRecipesResponse apply(List<Recipe> entities) {
        return GetRecipesResponse.builder()
                .recipes(entities.stream()
                        .map(recipe -> GetRecipesResponse.Recipe.builder()
                                .id(recipe.getId())
                                .name(recipe.getName())
                                .build())
                        .toList())
                .build();
    }

}
