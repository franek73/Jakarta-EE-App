package pl.edu.pg.eti.kask.app.recipe.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class RecipesModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Recipe {

        private UUID id;

        private String name;

        private Long version;

        private LocalDateTime creationDateTime;

        private LocalDateTime modificationDateTime;

    }

    @Singular
    private List<Recipe> recipes;

}
