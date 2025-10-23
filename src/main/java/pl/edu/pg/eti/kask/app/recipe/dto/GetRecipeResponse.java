package pl.edu.pg.eti.kask.app.recipe.dto;

import lombok.Builder;
import lombok.Data;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class GetRecipeResponse {

    @Data
    @Builder
    public static class Category {
        private UUID id;

        private String name;
    }

    private UUID id;

    private String name;

    private LocalDate creationDate;

    private String description;

    private Difficulty difficulty;

    private Category category;
}
