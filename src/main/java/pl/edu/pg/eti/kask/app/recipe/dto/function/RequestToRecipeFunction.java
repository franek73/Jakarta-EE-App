package pl.edu.pg.eti.kask.app.recipe.dto.function;

import pl.edu.pg.eti.kask.app.recipe.dto.PutRecipeRequest;
import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToRecipeFunction implements BiFunction<UUID, PutRecipeRequest, Recipe> {

    @Override
    public Recipe apply(UUID id, PutRecipeRequest request) {
        return Recipe.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .creationDate(LocalDate.now())
                .difficulty(Difficulty.Easy)
                .category(Category.builder()
                        .id(request.getCategory())
                        .build())
                .build();
    }

}
