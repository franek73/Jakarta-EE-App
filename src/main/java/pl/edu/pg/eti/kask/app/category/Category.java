package pl.edu.pg.eti.kask.app.category;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.Recipe;

import java.util.List;

@Data
public class Category {

    private String name;

    private String description;

    private List<Recipe> recipes;
}