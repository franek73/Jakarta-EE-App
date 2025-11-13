package pl.edu.pg.eti.kask.app.recipe.service.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;
import pl.edu.pg.eti.kask.app.recipe.respository.api.RecipeRepository;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Inject
    public RecipeServiceImpl(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<List<Recipe>> findAllByCategory(UUID id) {
        return categoryRepository.find(id)
                .map(recipeRepository::findAllByCategory);
    }

    @Override
    public Optional<List<Recipe>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(recipeRepository::findAllByUser);
    }

    @Override
    public Optional<Recipe> findByIdAndUser(UUID id, User user) {
        return recipeRepository.findByIdAndUser(id, user);
    }

    @Override
    public Optional<Recipe> find(UUID id) {
        return recipeRepository.find(id);
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    @Transactional
    public void create(Recipe entity) {
        if (recipeRepository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Recipe already exists.");
        }
        if (categoryRepository.find(entity.getCategory().getId()).isEmpty()) {
            throw new IllegalArgumentException("Category does not exists.");
        }

        recipeRepository.create(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        recipeRepository.delete(id);
    }

    @Override
    @Transactional
    public void update(Recipe entity) {
        recipeRepository.update(entity);
    }
}
