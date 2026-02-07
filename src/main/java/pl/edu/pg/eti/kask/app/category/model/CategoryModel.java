package pl.edu.pg.eti.kask.app.category.model;

import lombok.*;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeModel;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class CategoryModel {

    private UUID id;

    private String name;

    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<RecipeModel> recipes;
}
