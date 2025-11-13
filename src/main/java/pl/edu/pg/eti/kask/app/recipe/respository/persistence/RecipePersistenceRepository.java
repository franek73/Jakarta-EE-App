package pl.edu.pg.eti.kask.app.recipe.respository.persistence;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.respository.api.RecipeRepository;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class RecipePersistenceRepository implements RecipeRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Recipe> findByIdAndUser(UUID id, User user) {
        try {
            return Optional.of(em.createQuery("select r from Recipe r where r.id = :id and r.author = :author", Recipe.class)
                    .setParameter("author", user)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Recipe> findAllByUser(User user) {
        return em.createQuery("select r from Recipe r where r.author = :author", Recipe.class)
                .setParameter("author", user)
                .getResultList();
    }

    @Override
    public List<Recipe> findAllByCategory(Category category) {
        return em.createQuery("select r from Recipe r where r.category = :category", Recipe.class)
                .setParameter("category", category)
                .getResultList();
    }

    @Override
    public Optional<Recipe> find(UUID id) {
       return Optional.ofNullable(em.find(Recipe.class, id));
    }

    @Override
    public List<Recipe> findAll() {
        return em.createQuery("select r from Recipe r", Recipe.class).getResultList();
    }

    @Override
    public void create(Recipe entity) {
        em.persist(entity);
    }

    @Override
    public void delete(UUID id) {
        em.remove(em.find(Recipe.class, id));
    }

    @Override
    public void update(Recipe entity) {
        em.merge(entity);
    }
}
