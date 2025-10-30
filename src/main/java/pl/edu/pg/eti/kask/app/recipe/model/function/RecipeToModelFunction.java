package pl.edu.pg.eti.kask.app.recipe.model.function;

import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeModel;

import java.io.Serializable;
import java.util.function.Function;

public class RecipeToModelFunction implements Function<Recipe, RecipeModel>, Serializable {

    @Override
    public RecipeModel apply(Recipe entity) {
        return RecipeModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .difficulty(entity.getDifficulty())
                .creationDate(entity.getCreationDate())
                .category(entity.getCategory().getName())
                .build();
    }
}
