package pl.edu.pg.eti.kask.app.recipe.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetRecipesResponse {

    @Data
    @Builder
    public static class Recipe {
        private UUID id;

        private String name;
    }

    @Singular
    private List<Recipe> recipes;
}
