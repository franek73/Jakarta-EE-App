package pl.edu.pg.eti.kask.app.recipe.service.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Inject
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> find(UUID id) {
        return categoryRepository.find(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void create(Category entity) {
        categoryRepository.create(entity);
    }

    @Override
    public void delete(UUID id) {
        categoryRepository.delete(id);
    }

    @Override
    public void update(Category entity) {
        categoryRepository.update(entity);
    }
}
