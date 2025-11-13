package pl.edu.pg.eti.kask.app.recipe.service.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@Log
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Inject
    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Category> find(UUID id) {
        Optional<Category> category = repository.find(id);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void create(Category entity) {
        if (repository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Category already exists.");
        }

        repository.create(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public void update(Category entity) {
        repository.update(entity);
    }
}
