package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.model.RecipesModel;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.io.Serializable;

@RequestScoped
@Named
public class RecipeList implements Serializable {

    private RecipeService service;

    private RecipesModel recipes;

    private final ModelFunctionFactory factory;

    @Inject
    public RecipeList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(RecipeService service) {
        this.service = service;
    }

    public RecipesModel getRecipes() {
        if (recipes == null) {
            recipes = factory.recipesToModel().apply(service.findAllForCallerPrincipal());
        }
        return recipes;
    }

    public String deleteAction(RecipesModel.Recipe recipe) {
        service.deleteForCallerPrincipal(recipe.getId());
        return "/recipe/recipe_list?faces-redirect=true";
    }

}
