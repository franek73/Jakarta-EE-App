package pl.edu.pg.eti.kask.app.recipe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetCategoriesResponse {

    @Data
    @Builder
    public static class Category {

        private UUID id;

        private String name;
    }

    private List<Category> categories;
}
