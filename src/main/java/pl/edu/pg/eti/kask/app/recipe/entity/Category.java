package pl.edu.pg.eti.kask.app.recipe.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Category implements Serializable {

    private UUID id;

    private String name;

    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Recipe> recipes;
}