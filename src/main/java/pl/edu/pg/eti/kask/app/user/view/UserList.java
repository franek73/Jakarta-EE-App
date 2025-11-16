package pl.edu.pg.eti.kask.app.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.app.component.ModelFunctionFactory;
import pl.edu.pg.eti.kask.app.user.model.UsersModel;
import pl.edu.pg.eti.kask.app.user.service.UserService;

@RequestScoped
@Named
public class UserList {

    private final UserService service;

    private UsersModel users;

    private final ModelFunctionFactory factory;

    @Inject
    public UserList(UserService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    public UsersModel getUsers() {
        if (users == null) {
            users = factory.usersToModel().apply(service.findAll());
        }
        return users;
    }

    public String deleteAction(UsersModel.User user) {
        service.delete(user.getId());
        return "user_list?faces-redirect=true";
    }

}

