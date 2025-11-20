package pl.edu.pg.eti.kask.app.recipe.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryEditModel;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeEditModel;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.function.BiFunction;

public class UpdateCategoryWithModelFunction implements BiFunction<Category, CategoryEditModel, Category>, Serializable {

    @Override
    @SneakyThrows
    public Category apply(Category entity, CategoryEditModel model) {
        return Category.builder()
                .id(entity.getId())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }
}
