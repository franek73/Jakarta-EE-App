package pl.edu.pg.eti.kask.app.user.dto.function;

import pl.edu.pg.eti.kask.app.user.dto.PutPasswordRequest;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.time.LocalDate;
import java.util.function.BiFunction;

public class UpdateUserPasswordWithRequestFunction implements BiFunction<User, PutPasswordRequest, User> {

    @Override
    public User apply(User entity, PutPasswordRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .email(entity.getEmail())
                .name(entity.getName())
                .password(request.getPassword())
                .registeredDate(entity.getRegisteredDate())
                .build();
    }

}

