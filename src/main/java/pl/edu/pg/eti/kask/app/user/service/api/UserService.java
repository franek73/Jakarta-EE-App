package pl.edu.pg.eti.kask.app.user.service.api;

import pl.edu.pg.eti.kask.app.service.api.Service;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface UserService extends Service<User, UUID> {

    public Optional<User> findByLogin(String login);

    public void createAvatar(UUID id, InputStream is, String originalFilename);

    public void deleteAvatar(UUID id);

    public void updateAvatar(UUID id, InputStream is, String originalFilename);

    Optional<byte[]> findAvatar(UUID id);
}
