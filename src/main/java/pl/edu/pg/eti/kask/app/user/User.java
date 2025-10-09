package pl.edu.pg.eti.kask.app.user;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.Recipe;
import java.time.LocalDate;
import java.util.List;

@Data
public class User {

    private int id;

    private String userName;

    private LocalDate registeredDate;

    private Role role;

    List<Recipe> userRecipes;
}
