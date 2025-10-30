package pl.edu.pg.eti.kask.app.recipe.model;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class RecipeEditModel {

    private String name;

    private String description;

    private Difficulty difficulty;
}
