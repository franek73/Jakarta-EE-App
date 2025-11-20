package pl.edu.pg.eti.kask.app.recipe.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToCategoryFunction implements Function<CategoryCreateModel, Category>, Serializable {

    @Override
    @SneakyThrows
    public Category apply(CategoryCreateModel model) {
        return Category.builder()
                .id(model.getId())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }
}
