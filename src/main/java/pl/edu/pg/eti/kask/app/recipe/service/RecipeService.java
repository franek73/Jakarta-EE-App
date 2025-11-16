package pl.edu.pg.eti.kask.app.recipe.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;
import pl.edu.pg.eti.kask.app.recipe.respository.api.RecipeRepository;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class RecipeService {

    private RecipeRepository recipeRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

    @Inject
    public RecipeService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Optional<List<Recipe>> findAllByCategory(UUID id) {
        return categoryRepository.find(id)
                .map(recipeRepository::findAllByCategory);
    }

    public Optional<List<Recipe>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(recipeRepository::findAllByUser);
    }

    public Optional<Recipe> findByIdAndUser(UUID id, User user) {
        return recipeRepository.findByIdAndUser(id, user);
    }

    public Optional<Recipe> find(UUID id) {
        return recipeRepository.find(id);
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public void create(Recipe entity) {
        if (recipeRepository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Recipe already exists.");
        }
        if (categoryRepository.find(entity.getCategory().getId()).isEmpty()) {
            throw new IllegalArgumentException("Category does not exists.");
        }

        recipeRepository.create(entity);
    }

    public void delete(UUID id) {
        recipeRepository.delete(id);
    }

    public void update(Recipe entity) {
        recipeRepository.update(entity);
    }
}
