package pl.edu.pg.eti.kask.app.recipe.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.model.CategoryCreateModel;
import pl.edu.pg.eti.kask.app.recipe.service.CategoryService;

import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class CategoryCreate implements Serializable {

    private final CategoryService categoryService;

    private final ModelFunctionFactory factory;

    @Getter
    private CategoryCreateModel category;

    @Inject
    public CategoryCreate(CategoryService categoryService, ModelFunctionFactory factory) {
        this.categoryService = categoryService;
        this.factory = factory;
    }

    public void init() {
        if (category == null) {
            category = CategoryCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
        }

    }

    public String cancelAction() {
        return "/category/category_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        categoryService.create(factory.modelToCategory().apply(category));
        return "/category/category_view.xhtml?faces-redirect=true&amp;id=" + category.getId();
    }

}
