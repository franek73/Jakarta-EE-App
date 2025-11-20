package pl.edu.pg.eti.kask.app.recipe.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCategoryResponse {

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
    }

    private UUID id;

    private String name;

    private String description;

    @Singular
    private List<Recipe> recipes;
}
