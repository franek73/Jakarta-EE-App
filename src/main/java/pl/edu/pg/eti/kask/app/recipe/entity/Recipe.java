package pl.edu.pg.eti.kask.app.recipe.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.user.entity.User;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "recipes")
public class Recipe implements Serializable {
    @Id
    private UUID id;

    private String name;

    private LocalDate creationDate;

    private String description;

    private Difficulty difficulty;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_name")
    private User author;
}
