package pl.edu.pg.eti.kask.app.category.dto.function;

import pl.edu.pg.eti.kask.app.category.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.app.category.entity.Category;

import java.util.List;
import java.util.function.Function;

public class CategoriesToResponseFunction implements Function<List<Category>, GetCategoriesResponse> {

    @Override
    public GetCategoriesResponse apply(List<Category> entities) {
        return GetCategoriesResponse.builder()
                .categories(entities.stream()
                        .map(category -> GetCategoriesResponse.Category.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                        .toList())
                .build();
    }

}
