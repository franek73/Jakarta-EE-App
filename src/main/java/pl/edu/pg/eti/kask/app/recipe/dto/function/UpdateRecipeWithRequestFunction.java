package pl.edu.pg.eti.kask.app.recipe.dto.function;

import pl.edu.pg.eti.kask.app.recipe.dto.PatchRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;

import java.util.function.BiFunction;

public class UpdateRecipeWithRequestFunction implements BiFunction<Recipe, PatchRecipeRequest, Recipe> {

    @Override
    public Recipe apply(Recipe entity, PatchRecipeRequest request) {
        return Recipe.builder()
                .id(entity.getId())
                .name(request.getName())
                .description(request.getDescription())
                .difficulty(request.getDifficulty())
                .category(entity.getCategory())
                .author(entity.getAuthor())
                .creationDate(entity.getCreationDate())
                .build();
    }


}
