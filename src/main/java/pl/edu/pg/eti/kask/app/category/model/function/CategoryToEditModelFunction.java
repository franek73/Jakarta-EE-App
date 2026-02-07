package pl.edu.pg.eti.kask.app.category.model.function;

import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.category.model.CategoryEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class CategoryToEditModelFunction implements Function<Category, CategoryEditModel>, Serializable {

    @Override
    public CategoryEditModel apply(Category entity) {
        return CategoryEditModel.builder()
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
