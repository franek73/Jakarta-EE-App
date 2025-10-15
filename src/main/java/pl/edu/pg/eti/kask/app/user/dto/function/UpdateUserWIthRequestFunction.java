package pl.edu.pg.eti.kask.app.user.dto.function;

import pl.edu.pg.eti.kask.app.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.time.LocalDate;
import java.util.function.BiFunction;

public class UpdateUserWIthRequestFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(request.getLogin())
                .email(request.getEmail())
                .name(request.getName())
                .password(entity.getPassword())
                .registeredDate(entity.getRegisteredDate())
                .build();
    }
}
