package pl.edu.pg.eti.kask.app.category.model.function;

import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.category.model.CategoryEditModel;

import java.io.Serializable;
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
