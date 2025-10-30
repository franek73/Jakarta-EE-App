package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.model.CategoriesModel;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;

import java.io.Serializable;

@RequestScoped
@Named
public class CategoryList implements Serializable {

    private final CategoryService service;

    private CategoriesModel categories;

    private final ModelFunctionFactory factory;

    @Inject
    public CategoryList(CategoryService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public CategoriesModel getCategories() {
        if (categories == null) {
            categories = factory.categoriesToModel().apply(service.findAll());
        }
        return categories;
    }

    public String deleteAction(CategoriesModel.Category category) {
        service.delete(category.getId());
        return "category_list?faces-redirect=true";
    }


}
