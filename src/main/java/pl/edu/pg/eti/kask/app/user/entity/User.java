package pl.edu.pg.eti.kask.app.user.entity;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class User implements Serializable {

    private UUID id;

    private String login;

    private String name;

    private String email;

    private LocalDate registeredDate;

    private Role role;

    @ToString.Exclude
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    String avatarFilename;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Recipe> userRecipes;
}
