package pl.edu.pg.eti.kask.app.recipe.respository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.edu.pg.eti.kask.app.category.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe_;
import pl.edu.pg.eti.kask.app.recipe.respository.api.RecipeRepository;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class RecipePersistenceRepository implements RecipeRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Recipe> findByIdAndUser(UUID id, User user) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
            Root<Recipe> root = query.from(Recipe.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Recipe_.author), user),
                            cb.equal(root.get(Recipe_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Recipe> findAllByCategoryAndUser(Category category, User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        query.select(root)
                .where(cb.equal(root.get(Recipe_.category), category))
                .where(cb.equal(root.get(Recipe_.author), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Recipe> findAllByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        query.select(root)
                .where(cb.equal(root.get(Recipe_.author), user));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Recipe> findAllByCategory(Category category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        query.select(root)
                .where(cb.equal(root.get(Recipe_.category), category));
        return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Recipe> find(UUID id) {
       return Optional.ofNullable(em.find(Recipe.class, id));
    }

    @Override
    public List<Recipe> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> root = query.from(Recipe.class);
        query.select(root);
        return em.createQuery(query).getResultList();
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
