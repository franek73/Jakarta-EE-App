package pl.edu.pg.eti.kask.app.user.repository.api;

import pl.edu.pg.eti.kask.app.repository.api.Repository;
import pl.edu.pg.eti.kask.app.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends Repository<User, UUID> {

    Optional<User> findByLogin(String login);
}
