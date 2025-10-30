package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.model.RecipesModel;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;

import java.io.Serializable;

@RequestScoped
@Named
public class RecipeList implements Serializable {

    private final RecipeService service;

    private RecipesModel recipes;

    private final ModelFunctionFactory factory;

    @Inject
    public RecipeList(RecipeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public RecipesModel getRecipes() {
        if (recipes == null) {
            recipes = factory.recipesToModel().apply(service.findAll());
        }
        return recipes;
    }

    public String deleteAction(RecipesModel.Recipe recipe) {
        service.delete(recipe.getId());
        return "/recipe/recipe_list?faces-redirect=true";
    }

}
