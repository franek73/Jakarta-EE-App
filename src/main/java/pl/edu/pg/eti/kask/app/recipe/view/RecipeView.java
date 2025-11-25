package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeModel;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class RecipeView implements Serializable {

    private RecipeService service;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private RecipeModel recipe;

    @Inject
    public RecipeView(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(RecipeService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Recipe> recipe = service.findForCallerPrincipal(id);
        if (recipe.isPresent()) {
            this.recipe = factory.recipeToModel().apply(recipe.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
        }
    }


}
