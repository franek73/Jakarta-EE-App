package pl.edu.pg.eti.kask.app.user.dto.function;

import pl.edu.pg.eti.kask.app.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.function.Function;

public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .name(user.getName())
                .email(user.getEmail())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}
