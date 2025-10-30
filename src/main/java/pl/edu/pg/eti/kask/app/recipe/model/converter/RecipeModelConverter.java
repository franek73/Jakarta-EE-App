package pl.edu.pg.eti.kask.app.recipe.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.entity.Recipe;
import pl.edu.pg.eti.kask.app.recipe.model.RecipeModel;
import pl.edu.pg.eti.kask.app.recipe.service.api.RecipeService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = RecipeModel.class, managed = true)
public class RecipeModelConverter implements Converter<RecipeModel> {

    private final RecipeService service;

    private final ModelFunctionFactory factory;

    @Inject
    public RecipeModelConverter(RecipeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public RecipeModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Recipe> recipe = service.find(UUID.fromString(value));
        return recipe.map(factory.recipeToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, RecipeModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
