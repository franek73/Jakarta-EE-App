package pl.edu.pg.eti.kask.app.user.controller.api;

import pl.edu.pg.eti.kask.app.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.app.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.app.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.app.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface UserController {

    GetUserResponse getUser(UUID id);

    GetUsersResponse getUsers();

    void putUser(UUID id, PutUserRequest user);

    void patchUser(UUID id, PatchUserRequest user);

    void deleteUser(UUID id);

    Optional<byte[]> getUserAvatar(UUID id);

    void putUserAvatar(UUID id, InputStream avatar, String originalFilename);

    void patchUserAvatar(UUID id, InputStream avatar, String originalFilename);

    void deleteUserAvatar(UUID id);
}
