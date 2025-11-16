package pl.edu.pg.eti.kask.app.user.model.function;

import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.model.UserModel;

import java.io.Serializable;
import java.util.function.Function;


public class UserToModelFunction implements Function<User, UserModel>, Serializable {

    @Override
    public UserModel apply(User entity) {
        return UserModel.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }

}
