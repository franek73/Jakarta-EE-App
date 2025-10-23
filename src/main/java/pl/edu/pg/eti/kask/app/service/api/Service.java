package pl.edu.pg.eti.kask.app.service.api;

import java.util.List;
import java.util.Optional;

public interface Service<E, K> {
    Optional<E> find(K id);

    List<E> findAll();

    void create(E entity);

    void delete(K id);

    void update(E entity);
}
