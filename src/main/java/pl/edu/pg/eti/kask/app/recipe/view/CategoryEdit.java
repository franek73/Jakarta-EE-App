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
import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.recipe.entity.Difficulty;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryEditModel;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeEditModel;
import pl.edu.pg.eti.kask.app.recipe.service.CategoryService;
import pl.edu.pg.eti.kask.app.recipe.service.RecipeService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class CategoryEdit implements Serializable {

    private CategoryService service;

    private ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private CategoryEditModel category;

    @Inject
    public CategoryEdit(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(CategoryService service) {
        this.service = service;
    }

    public void init() throws IOException {
        Optional<Category> category = service.find(id);
        if (category.isPresent()) {
            this.category = factory.categoryToEditModel().apply(category.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateCategory().apply(service.find(id).orElseThrow(), category));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
