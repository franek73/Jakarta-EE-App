package pl.edu.pg.eti.kask.app.recipe.model.function;

import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.model.CategoriesModel;

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
