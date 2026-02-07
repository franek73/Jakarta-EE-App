package pl.edu.pg.eti.kask.app.recipe.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeCreateModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.Function;

public class ModelToRecipeFunction implements Function<RecipeCreateModel, Recipe>, Serializable {

    @Override
    @SneakyThrows
    public Recipe apply(RecipeCreateModel model) {
        return Recipe.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .creationDate(LocalDate.now())
                .difficulty(model.getDifficulty())
                .category(Category.builder()
                        .id(model.getCategory().getId())
                        .build())
                .build();
    }
}
