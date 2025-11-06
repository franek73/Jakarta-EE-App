package pl.edu.pg.eti.kask.app.recipe.dto.function;

import pl.edu.pg.eti.kask.app.recipe.dto.PutCategoryRequest;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToCategoryFunction  implements BiFunction<UUID, PutCategoryRequest, Category> {

    @Override
    public Category apply(UUID id, PutCategoryRequest request) {
        return Category.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
