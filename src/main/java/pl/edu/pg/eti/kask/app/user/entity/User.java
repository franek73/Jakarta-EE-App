package pl.edu.pg.eti.kask.app.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String login;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "role")
    private String role;

    @ToString.Exclude
    @Column(nullable = false)
    private String password;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    String avatarFilename;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Recipe> userRecipes;
}
