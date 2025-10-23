package pl.edu.pg.eti.kask.app.recipe.respository.api;

import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.repository.api.Repository;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends Repository<Recipe, UUID> {

    Optional<Recipe> findByIdAndUser(UUID id, User user);

    List<Recipe> findAllByUser(User user);

    List<Recipe> findAllByCategory(Category category);
}
