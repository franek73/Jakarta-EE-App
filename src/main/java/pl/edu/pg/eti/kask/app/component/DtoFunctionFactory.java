package pl.edu.pg.eti.kask.app.component;

import pl.edu.pg.eti.kask.app.user.dto.function.*;

public class DtoFunctionFactory {

    public UserToResponseFunction userToResponse() {
        return new UserToResponseFunction();
    }

    public UsersToResponseFunction usersToResponse() {
        return new UsersToResponseFunction();
    }

    public RequestToUserFunction requestToUser() {
        return new RequestToUserFunction();
    }

    public UpdateUserWIthRequestFunction updateUser() {
        return new UpdateUserWIthRequestFunction();
    }

    public UpdateUserPasswordWithRequestFunction updateUserPassword() {
        return new UpdateUserPasswordWithRequestFunction();
    }
}
