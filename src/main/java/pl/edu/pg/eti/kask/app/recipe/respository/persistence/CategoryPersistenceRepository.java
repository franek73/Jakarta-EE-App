package pl.edu.pg.eti.kask.app.recipe.respository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class CategoryPersistenceRepository implements CategoryRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Category> find(UUID id) {
        return Optional.ofNullable(em.find(Category.class, id));
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }

    @Override
    public void create(Category entity) {
        em.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        Category category = em.find(Category.class, id);
        em.refresh(category);

        em.remove(category);
    }

    @Override
    public void update(Category entity) {
        em.merge(entity);
    }
}
