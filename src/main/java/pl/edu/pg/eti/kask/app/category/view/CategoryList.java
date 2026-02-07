package pl.edu.pg.eti.kask.app.category.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.category.model.CategoriesModel;
import pl.edu.pg.eti.kask.app.category.service.CategoryService;

import java.io.Serializable;
import java.util.UUID;

@RequestScoped
@Named
public class CategoryList implements Serializable {

    private CategoryService service;

    private CategoriesModel categories;

    private final ModelFunctionFactory factory;

    @Inject
    public CategoryList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(CategoryService service) {
        this.service = service;
    }

    public CategoriesModel getCategories() {
        if (categories == null) {
            categories = factory.categoriesToModel().apply(service.findAll());
        }
        return categories;
    }

    public void deleteAction(UUID id) {
        service.delete(id);
        categories = null;
    }


}
