package pl.edu.pg.eti.kask.app.recipe.dto;

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
public class PutRecipeRequest {

    private String name;

    private String description;

    private Difficulty difficulty;

    private UUID category;
}
