package pl.edu.pg.eti.kask.app.recipe.model;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class RecipeCreateModel {

    private UUID id;

    private String name;

    private String description;

    private Difficulty difficulty;

    private CategoryModel category;
}
