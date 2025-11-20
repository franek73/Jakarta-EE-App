package pl.edu.pg.eti.kask.app.recipe.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.respository.api.CategoryRepository;
import pl.edu.pg.eti.kask.app.recipe.respository.api.RecipeRepository;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.entity.UserRole;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final SecurityContext securityContext;

    @Inject
    public RecipeService(RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository,
                         UserRepository userRepository,
                         @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed(UserRole.ADMIN)
    public Optional<List<Recipe>> findAllByCategory(UUID id) {
        return categoryRepository.find(id)
                .map(recipeRepository::findAllByCategory);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public Optional<List<Recipe>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(recipeRepository::findAllByUser);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public Optional<Recipe> findByIdAndUser(UUID id, User user) {
        return recipeRepository.findByIdAndUser(id, user);
    }

    @RolesAllowed(UserRole.ADMIN)
    public Optional<Recipe> find(UUID id) {
        return recipeRepository.find(id);
    }

    @RolesAllowed(UserRole.ADMIN)
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public void create(Recipe recipe) {
        if (recipeRepository.find(recipe.getId()).isPresent()) {
            throw new IllegalArgumentException("Recipe already exists.");
        }
        if (categoryRepository.find(recipe.getCategory().getId()).isEmpty()) {
            throw new IllegalArgumentException("Category does not exists.");
        }

        recipeRepository.create(recipe);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public void delete(UUID id) {
        recipeRepository.delete(id);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public void update(Recipe recipe) {
        recipeRepository.update(recipe);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public Optional<Recipe> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return find(id);
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findByIdAndUser(id, user);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public List<Recipe> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return findAll();
        }
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAllByUser(user.getId()).orElseThrow();
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public void createForCallerPrincipal(Recipe recipe) {
        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        recipe.setAuthor(user);
        create(recipe);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public void updateForCallerPrincipal(Recipe recipe) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            update(recipe);
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        findByIdAndUser(recipe.getId(), user).orElseThrow(IllegalStateException::new);
        update(recipe);
    }

    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    public void deleteForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            delete(id);
        }

        User user = userRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        findByIdAndUser(id, user).orElseThrow(IllegalStateException::new);
        delete(id);
    }

    private void checkAdminRoleOrOwner(Optional<Recipe> recipe) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRole.USER)
                && recipe.isPresent()
                && recipe.get().getAuthor().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}
