package pl.edu.pg.eti.kask.app.recipe;

import java.time.LocalDate;
import lombok.*;
import pl.edu.pg.eti.kask.app.category.Category;
import pl.edu.pg.eti.kask.app.user.*;

@Data
public class Recipe {

    private String name;

    private LocalDate creationDate;

    private String description;

    private Difficulty difficulty;

    private Category category;

    private User author;
}
