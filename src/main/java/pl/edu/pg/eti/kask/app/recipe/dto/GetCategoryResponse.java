package pl.edu.pg.eti.kask.app.recipe.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetCategoryResponse {

    @Data
    @Builder
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
