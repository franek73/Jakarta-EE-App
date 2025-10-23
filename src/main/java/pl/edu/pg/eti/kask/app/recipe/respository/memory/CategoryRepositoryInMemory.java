package pl.edu.pg.eti.kask.app.recipe.respository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.datastore.component.DataStore;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class CategoryRepositoryInMemory implements CategoryRepository {

    private final DataStore store;

    @Inject
    public CategoryRepositoryInMemory(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Category> find(UUID id) {
        return store.findAllCategories().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Category> findAll() {
        return store.findAllCategories();
    }

    @Override
    public void create(Category entity) {
        store.createCategory(entity);
    }

    @Override
    public void delete(UUID id) {
        store.deleteCategory(id);
    }

    @Override
    public void update(Category entity) {
        store.updateCategory(entity);
    }
}
