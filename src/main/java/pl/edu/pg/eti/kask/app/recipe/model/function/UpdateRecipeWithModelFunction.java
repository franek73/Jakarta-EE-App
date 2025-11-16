package pl.edu.pg.eti.kask.app.recipe.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeEditModel;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiFunction;

public class UpdateRecipeWithModelFunction implements BiFunction<Recipe, RecipeEditModel, Recipe>, Serializable {

    @Override
    @SneakyThrows
    public Recipe apply(Recipe entity, RecipeEditModel model) {
        return Recipe.builder()
                .id(entity.getId())
                .name(model.getName())
                .description(model.getDescription())
                .creationDate(LocalDate.now())
                .difficulty(model.getDifficulty())
                .category(entity.getCategory())
                .author(User.builder()
                        .id(model.getAuthor().getId())
                        .build())
                .build();
    }
}
