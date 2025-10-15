package pl.edu.pg.eti.kask.app.datastore.component;

import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.serialization.component.CloningUtility;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataStore {

    private final Set<Category> categories = new HashSet<>();

    private final Set<Recipe> recipes = new HashSet<>();

    private final Set<User> users = new HashSet<>();

    private final CloningUtility cloningUtility;

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

}
