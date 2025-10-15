package pl.edu.pg.eti.kask.app.user.service.api;

import pl.edu.pg.eti.kask.app.user.entity.User;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    public Optional<User> find(UUID id);

    public Optional<User> findByLogin(String login);

    public List<User> findAll() ;

    public void create(User user);

    public void update(User user);

    public void delete(UUID id) ;

    public void createAvatar(UUID id, InputStream is, String originalFilename);

    public void deleteAvatar(UUID id);

    public void updateAvatar(UUID id, InputStream is, String originalFilename);

    Optional<byte[]> findAvatar(UUID id);
}
