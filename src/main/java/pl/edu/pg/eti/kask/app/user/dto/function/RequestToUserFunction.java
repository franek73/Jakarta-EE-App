package pl.edu.pg.eti.kask.app.user.dto.function;

import pl.edu.pg.eti.kask.app.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToUserFunction implements BiFunction<UUID, PutUserRequest, User> {

    @Override
    public User apply(UUID id, PutUserRequest request) {
        return User.builder()
                .id(id)
                .login(request.getLogin())
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword())
                .registeredDate(LocalDate.now())
                .build();
    }
}
