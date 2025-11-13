package pl.edu.pg.eti.kask.app.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextListener;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;
import pl.edu.pg.eti.kask.app.user.entity.Role;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.service.api.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import lombok.SneakyThrows;

@ApplicationScoped
public class InitializedData implements ServletContextListener {

    private final UserService userService;

    private final CategoryService categoryService;

    private final RecipeService recipeService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService, CategoryService categoryService, RecipeService recipeService,RequestContextController requestContextController) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.recipeService = recipeService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();

        if (userService.findByLogin("admin").isEmpty()) {

            User admin = User.builder()
                    .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                    .login("admin")
                    .email("admin@gmail.com")
                    .name("Admin")
                    .password("admin123")
                    .registrationDate(LocalDate.now())
                    .role(Role.Admin)
                    .build();

            User franek = User.builder()
                    .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                    .login("franek73")
                    .email("franek@gmail.com")
                    .name("Franek")
                    .password("franek123")
                    .registrationDate(LocalDate.now())
                    .role(Role.User)
                    .build();

            User maciek = User.builder()
                    .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                    .login("maniek")
                    .email("maciek@gmail.com")
                    .name("Maciek")
                    .password("maciek123")
                    .registrationDate(LocalDate.now())
                    .role(Role.User)
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
                    .author(maciek)
                    .build();

            recipeService.create(porridge);
            recipeService.create(omelet);
            recipeService.create(pizza);
        }

        requestContextController.deactivate();
    }

}
