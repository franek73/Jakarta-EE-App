package pl.edu.pg.eti.kask.app.recipe.model.function;

import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class RecipeToEditModelFunction implements Function<Recipe, RecipeEditModel>, Serializable {

    @Override
    public RecipeEditModel apply(Recipe entity) {
        return RecipeEditModel.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .difficulty(entity.getDifficulty())
                .build();
    }
}
