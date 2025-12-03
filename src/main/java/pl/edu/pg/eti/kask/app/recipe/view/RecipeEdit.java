package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeEditModel;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class RecipeEdit implements Serializable {

    private RecipeService service;

    private ModelFunctionFactory factory;

    private final FacesContext facesContext;

    @Getter
    private Difficulty[] difficulties;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private RecipeEditModel recipe;

    @Inject
    public RecipeEdit(ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setService(RecipeService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Recipe> recipe = service.findForCallerPrincipal(id);
        if (recipe.isPresent()) {
            this.recipe = factory.recipeToEditModel().apply(recipe.get());
            difficulties = Difficulty.values();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
        }
    }

    public String saveAction() throws IOException {
        try {
            service.updateForCallerPrincipal(factory.updateRecipe().apply(service.findForCallerPrincipal(id).orElseThrow(), recipe));
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        }
        catch (EJBException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null ;
        }

    }

}
