package pl.edu.pg.eti.kask.app.recipe.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.entity.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@Log
@NoArgsConstructor(force = true, access = AccessLevel.PUBLIC)
public class CategoryService {

    private final CategoryRepository repository;

    @Inject
    public CategoryService(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    public Optional<Category> find(UUID id) {
        Optional<Category> category = repository.find(id);
        return category;
    }

    @RolesAllowed({UserRole.ADMIN, UserRole.USER})
    public List<Category> findAll() {
        return repository.findAll();
    }

    @RolesAllowed(UserRole.ADMIN)
    public void create(Category entity) {
        if (repository.find(entity.getId()).isPresent()) {
            throw new IllegalArgumentException("Category already exists.");
        }

        repository.create(entity);
    }

    @RolesAllowed(UserRole.ADMIN)
    public void delete(UUID id) {
        repository.delete(id);
    }

    @RolesAllowed(UserRole.ADMIN)
    public void update(Category entity) {
        repository.update(entity);
    }
}
