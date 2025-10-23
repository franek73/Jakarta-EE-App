package pl.edu.pg.eti.kask.app.recipe.respository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.datastore.component.DataStore;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.respository.api.RecipeRepository;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class RecipeRepositoryInMemory implements RecipeRepository {

    private final DataStore store;

    @Inject
    public RecipeRepositoryInMemory(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Recipe> findByIdAndUser(UUID id, User user) {
        return store.findAllRecipes().stream()
                .filter(recipe -> recipe.getAuthor().equals(user))
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Recipe> findAllByUser(User user) {
        return store.findAllRecipes().stream()
                .filter(recipe -> user.equals(recipe.getAuthor()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAllByCategory(Category category) {
        return store.findAllRecipes().stream()
                .filter(recipe -> category.equals(recipe.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Recipe> find(UUID id) {
        return store.findAllRecipes().stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst();

    }

    @Override
    public List<Recipe> findAll() {
        return store.findAllRecipes();
    }

    @Override
    public void create(Recipe entity) {
        store.createRecipe(entity);
    }

    @Override
    public void delete(UUID id) {
        store.deleteRecipe(id);
    }

    @Override
    public void update(Recipe entity) {
        store.updateRecipe(entity);
    }
}
