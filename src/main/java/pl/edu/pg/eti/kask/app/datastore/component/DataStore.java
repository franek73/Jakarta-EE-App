package pl.edu.pg.eti.kask.app.datastore.component;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Category> categories = new HashSet<>();

    private final Set<Recipe> recipes = new HashSet<>();

    private final Set<User> users = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<User> findAllUsers() {
        return users.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(User value) throws IllegalArgumentException {
        if (users.stream().anyMatch(user -> user.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The user id \"%s\" is not unique".formatted(value.getId()));
        }
        users.add(cloningUtility.clone(value));
    }

    public synchronized void updateUser(User value) throws IllegalArgumentException {
        if (users.removeIf(user -> user.getId().equals(value.getId()))) {
            users.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteUser(UUID id) throws IllegalArgumentException {
        if (!users.removeIf(user -> user.getId().equals(id))) {
            throw new IllegalArgumentException("The user with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Recipe> findAllRecipes() {
        return recipes.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createRecipe(Recipe value) throws IllegalArgumentException {
        if (recipes.stream().anyMatch(recipe -> recipe.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The recipe id \"%s\" is not unique".formatted(value.getId()));
        }
        Recipe entity = cloneWithRelationships(value);
        recipes.add(entity);
    }

    public synchronized void updateRecipe(Recipe value) throws IllegalArgumentException {
        Recipe entity = cloneWithRelationships(value);
        if (recipes.removeIf(recipe -> recipe.getId().equals(value.getId()))) {
            recipes.add(entity);
        } else {
            throw new IllegalArgumentException("The recipe with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteRecipe(UUID id) throws IllegalArgumentException {
        if (!recipes.removeIf(recipe -> recipe.getId().equals(id))) {
            throw new IllegalArgumentException("The recipe with id \"%s\" does not exist".formatted(id));
        }
    }

    public synchronized List<Category> findAllCategories() {
        return categories.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createCategory(Category value) throws IllegalArgumentException {
        if (categories.stream().anyMatch(category -> category.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The category id \"%s\" is not unique".formatted(value.getId()));
        }
        categories.add(cloningUtility.clone(value));
    }

    public synchronized void updateCategory(Category value) throws IllegalArgumentException {
        Category entity = cloneWithRelationships(value);
        if (categories.removeIf(category -> category.getId().equals(value.getId()))) {
            categories.add(entity);
        } else {
            throw new IllegalArgumentException("The category with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    //TODO: what to do with recipes?
    public synchronized void deleteCategory(UUID id) throws IllegalArgumentException {
        if (!categories.removeIf(category -> category.getId().equals(id))) {
            throw new IllegalArgumentException("The category with id \"%s\" does not exist".formatted(id));
        }

    }

    private Recipe cloneWithRelationships(Recipe value) {
        Recipe entity = cloningUtility.clone(value);

        if (entity.getAuthor() != null) {
            entity.setAuthor(users.stream()
                    .filter(user -> user.getId().equals(value.getAuthor().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No user with id \"%s\".".formatted(value.getAuthor().getId()))));
        }

        if (entity.getCategory() != null) {
            entity.setCategory(categories.stream()
                    .filter(category -> category.getId().equals(value.getCategory().getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No category with id \"%s\".".formatted(value.getCategory().getId()))));
        }

        return entity;
    }

    private Category cloneWithRelationships(Category value) {
        Category entity = cloningUtility.clone(value);

        if (entity.getRecipes() != null) {
            entity.setRecipes(recipes.stream()
                    .filter(recipe -> recipe.getCategory().getId().equals(value.getId()))
                    .collect(Collectors.toList()));
        }

        return entity;
    }

}
