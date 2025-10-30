package pl.edu.pg.eti.kask.app.recipe.model.function;

import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryModel;

import java.io.Serializable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoryToModelFunction implements Function<Category, CategoryModel> , Serializable {

    private final RecipeToModelFunction recipeToModelFunction;

    public CategoryToModelFunction(RecipeToModelFunction recipeToModelFunction) {
        this.recipeToModelFunction = recipeToModelFunction;
    }

    @Override
    public CategoryModel apply(Category entity) {
        return CategoryModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .recipes(entity.getRecipes().stream()
                        .map(recipeToModelFunction)
                        .collect(Collectors.toList()))
                .build();
    }
}
