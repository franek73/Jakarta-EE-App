package pl.edu.pg.eti.kask.app.category.model.function;

import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.category.model.CategoriesModel;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class CategoriesToModelFunction implements Function<List<Category>, CategoriesModel>, Serializable {

    @Override
    public CategoriesModel apply(List<Category> entity) {
        return CategoriesModel.builder()
                .categories(entity.stream()
                        .map(category -> CategoriesModel.Category.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                        .toList())
                .build();
    }
}
