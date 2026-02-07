package pl.edu.pg.eti.kask.app.recipe.model.function;

import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeEditModel;
import pl.edu.pg.eti.kask.app.user.model.function.UserToModelFunction;

import java.io.Serializable;
import java.util.function.Function;
import pl.edu.pg.eti.kask.app.user.model.function.UserToModelFunction;

public class RecipeToEditModelFunction implements Function<Recipe, RecipeEditModel>, Serializable {

    private final UserToModelFunction userToModelFunction;

    public RecipeToEditModelFunction(UserToModelFunction userToModelFunction) {
        this.userToModelFunction = userToModelFunction;
    }

    @Override
    public RecipeEditModel apply(Recipe entity) {
        return RecipeEditModel.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .difficulty(entity.getDifficulty())
                .author(userToModelFunction.apply(entity.getAuthor()))
                .version(entity.getVersion())
                .build();
    }
}
