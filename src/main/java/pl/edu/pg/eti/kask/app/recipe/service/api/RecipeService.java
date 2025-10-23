package pl.edu.pg.eti.kask.app.recipe.service.api;

import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.service.api.Service;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeService extends Service<Recipe, UUID> {

    Optional<List<Recipe>> findAllByCategory(UUID id);

    Optional<List<Recipe>> findAllByUser(UUID id);

    Optional<Recipe> findByIdAndUser(UUID id, User user);
}
