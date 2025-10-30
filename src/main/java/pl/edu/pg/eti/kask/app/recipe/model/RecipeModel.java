package pl.edu.pg.eti.kask.app.recipe.model;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RecipeModel {

    private UUID id;

    private String name;

    private LocalDate creationDate;

    private String description;

    private Difficulty difficulty;

    private String category;
}
