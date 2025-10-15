package pl.edu.pg.eti.kask.app.recipe.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;
import pl.edu.pg.eti.kask.app.user.entity.User;

@Data
public class Recipe implements Serializable {

    private UUID id;

    private String name;

    private LocalDate creationDate;

    private String description;

    private Difficulty difficulty;

    private Category category;

    private User author;
}
