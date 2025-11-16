package pl.edu.pg.eti.kask.app.recipe.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@Log
@NoArgsConstructor(force = true)
public class CategoryService {

    private final CategoryRepository repository;

    @Inject
    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    public Optional<Category> find(UUID id) {
        Optional<Category> category = repository.find(id);
        return category;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public void create(Category entity) {
        if (repository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Category already exists.");
        }

        repository.create(entity);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

    public void update(Category entity) {
        repository.update(entity);
    }
}
