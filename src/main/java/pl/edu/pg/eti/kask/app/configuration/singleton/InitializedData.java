package pl.edu.pg.eti.kask.app.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.service.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;
import pl.edu.pg.eti.kask.app.user.entity.UserRole;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.service.UserService;

import java.time.LocalDate;
import java.util.UUID;
import lombok.SneakyThrows;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({UserRole.ADMIN, UserRole.USER})
@RunAs(UserRole.ADMIN)
@Log
public class InitializedData {

    private UserService userService;

    private CategoryService categoryService;

    private RecipeService recipeService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @EJB
    public void setRecipeService(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userService.findByLogin("admin").isEmpty()) {

            User admin = User.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .login("admin")
                    .email("admin@gmail.com")
                    .name("Admin")
                    .password("admin123")
                    .registrationDate(LocalDate.now())
                    .role(UserRole.ADMIN)
                    .build();

            User franek = User.builder()
                    .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                    .login("franek73")
                    .email("franek@gmail.com")
                    .name("Franek")
                    .password("franek123")
                    .registrationDate(LocalDate.now())
                    .role(UserRole.USER)
                    .build();

            User maciek = User.builder()
                    .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                    .login("maniek")
                    .email("maciek@gmail.com")
                    .name("Maciek")
                    .password("maciek123")
                    .registrationDate(LocalDate.now())
                    .role(UserRole.USER)
                    .build();

            userService.create(admin);
            userService.create(franek);
            userService.create(maciek);

            Category breakfast = Category.builder()
                    .id(UUID.fromString("f5875513-bf7b-4ae1-b8a5-5b70a1b90e76"))
                    .name("Breakfast")
                    .description("Breakfast")
                    .build();

            Category diner = Category.builder()
                    .id(UUID.fromString("5d1da2ae-6a14-4b6d-8b4f-d117867118d4"))
                    .name("Diner")
                    .description("Diner")
                    .build();

            categoryService.create(breakfast);
            categoryService.create(diner);

            Recipe porridge = Recipe.builder()
                    .id(UUID.fromString("525d3e7b-bb1f-4c13-bf17-926d1a12e4c0"))
                    .name("Porridge")
                    .description("Porridge")
                    .difficulty(Difficulty.Easy)
                    .creationDate(LocalDate.now())
                    .category(breakfast)
                    .author(franek)
                    .build();

            Recipe omelet = Recipe.builder()
                    .id(UUID.fromString("cc0b0577-bb6f-45b7-81d6-3db88e6ac19f"))
                    .name("Omelet")
                    .description("Omelet")
                    .difficulty(Difficulty.Hard)
                    .creationDate(LocalDate.now())
                    .category(breakfast)
                    .author(maciek)
                    .build();

            Recipe pizza = Recipe.builder()
                    .id(UUID.fromString("f08ef7e3-7f2a-4378-b1fb-2922d730c70d"))
                    .name("Pizza")
                    .description("Pizza")
                    .difficulty(Difficulty.Medium)
                    .creationDate(LocalDate.now())
                    .category(diner)
                    .author(franek)
                    .build();

            Recipe spaghetti = Recipe.builder()
                    .id(UUID.fromString("e08ef7e3-7f2a-4378-b1fb-2922d730c70d"))
                    .name("Spaghetti")
                    .description("Spaghetti")
                    .difficulty(Difficulty.Medium)
                    .creationDate(LocalDate.now())
                    .category(diner)
                    .author(franek)
                    .build();

            recipeService.create(porridge);
            recipeService.create(omelet);
            recipeService.create(pizza);
            recipeService.create(spaghetti);
        }
    }

}
