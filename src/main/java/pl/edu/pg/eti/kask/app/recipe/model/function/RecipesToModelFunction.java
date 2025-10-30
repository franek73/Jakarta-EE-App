package pl.edu.pg.eti.kask.app.recipe.model.function;

import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipesModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class RecipesToModelFunction implements Function<List<Recipe>, RecipesModel>, Serializable {

    @Override
    public RecipesModel apply(List<Recipe> entity) {
        return RecipesModel.builder()
                .recipes(entity.stream()
                        .map(recipe -> RecipesModel.Recipe.builder()
                                .id(recipe.getId())
                                .name(recipe.getName())
                                .build())
                        .toList())
                .build();
    }
}
