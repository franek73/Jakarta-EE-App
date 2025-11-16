package pl.edu.pg.eti.kask.app.user.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.model.UserModel;
import pl.edu.pg.eti.kask.app.user.service.UserService;

import java.util.Optional;

@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    private final UserService service;

    private final ModelFunctionFactory factory;

    @Inject
    public UserModelConverter(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<User> user = service.findByLogin(value);
        return user.map(factory.userToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null ? "" : value.getLogin();
    }

}

