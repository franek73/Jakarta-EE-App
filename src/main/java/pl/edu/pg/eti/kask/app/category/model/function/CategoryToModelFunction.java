package pl.edu.pg.eti.kask.app.category.model.function;

import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.category.model.CategoryModel;
import pl.edu.pg.eti.kask.app.recipe.model.function.RecipeToModelFunction;

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
