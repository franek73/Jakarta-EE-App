package pl.edu.pg.eti.kask.app.authorization.interceptor;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;
import pl.edu.pg.eti.kask.app.authorization.exception.NoPrincipalException;
import pl.edu.pg.eti.kask.app.authorization.exception.NoRolesException;
import pl.edu.pg.eti.kask.app.authorization.interceptor.binding.AllowAdminOrRecipeAuthor;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;
import pl.edu.pg.eti.kask.app.user.entity.UserRole;

import java.util.Optional;
import java.util.UUID;

@Interceptor
@AllowAdminOrRecipeAuthor
@Priority(10)
public class AllowAdminOrRecipeAuthorInterceptor {

    private final SecurityContext securityContext;

    private final RecipeService recipeService;

    @Inject
    public AllowAdminOrRecipeAuthorInterceptor(SecurityContext securityContext, RecipeService recipeService) {
        this.securityContext = securityContext;
        this.recipeService = recipeService;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NoPrincipalException();
        }
        if (authorized(context)) {
            return context.proceed();
        }
        throw new NoRolesException();
    }

    private boolean authorized(InvocationContext context) {
        if (securityContext.isCallerInRole(UserRole.ADMIN)) {
            return true;
        } else if (securityContext.isCallerInRole(UserRole.USER)) {
            Object provided = context.getParameters()[0];
            Optional<Recipe> recipe;
            if (provided instanceof Recipe) {
                recipe = recipeService.find(((Recipe) provided).getId());
            } else if (provided instanceof UUID) {
                recipe = recipeService.find((UUID) provided);
            } else {
                throw new IllegalStateException("No recipe of UUID as first method parameter.");
            }

            return recipe.isPresent()
                    && recipe.get().getAuthor().getLogin().equals(securityContext.getCallerPrincipal().getName());
        }
        return false;
    }

}
