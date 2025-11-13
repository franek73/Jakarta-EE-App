package pl.edu.pg.eti.kask.app.user.service.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.app.user.service.api.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    private final String avatarDir;

    @Inject
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
        this.avatarDir = "C:/temp/avatar";

        try {
            Path path = Paths.get(avatarDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create a catalog for avatars images: " + avatarDir, e);
        }
    }

    @Override
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void create(User entity) {
        repository.create(entity);
    }

    @Override
    @Transactional
    public void update(User entity) {
        repository.update(entity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repository.delete(id);
    }

    @Override
    public Optional<byte[]> findAvatar(UUID id) {
        return repository.find(id)
                .flatMap(user -> {
                    String filename = user.getAvatarFilename();
                    if (filename == null) {
                        return Optional.empty();
                    }

                    Path path = Paths.get(avatarDir, filename);
                    try {
                        if (Files.exists(path)) {
                            return Optional.of(Files.readAllBytes(path));
                        } else {
                            return Optional.empty();
                        }
                    } catch (IOException ex) {
                        throw new IllegalStateException(ex);
                    }
                });
    }

    @Override
    public void updateAvatar(UUID id, InputStream is, String originalFilename) {
        repository.find(id).ifPresent(user -> {
            try {
                deleteAvatarFile(user.getAvatarFilename());

                String extension = getFileExtension(originalFilename);
                String newFilename = id + extension;
                Path target = Paths.get(avatarDir, newFilename);
                Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);

                user.setAvatarFilename(newFilename);
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Override
    public void createAvatar(UUID id, InputStream is, String originalFilename) {
        repository.find(id).ifPresent(user -> {
            try {
                deleteAvatarFile(user.getAvatarFilename());

                String extension = getFileExtension(originalFilename);
                String newFilename = id + extension;
                Path target = Paths.get(avatarDir, newFilename);
                Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);

                user.setAvatarFilename(newFilename);
                repository.update(user);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    @Override
    public void deleteAvatar(UUID id) {
        repository.find(id).ifPresent(user -> {
            try {
                deleteAvatarFile(user.getAvatarFilename());
                user.setAvatarFilename(null);
                repository.update(user);
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    private void deleteAvatarFile(String filename) throws IOException {
        if (filename == null) return;
        Path path = Paths.get(avatarDir, filename);
        Files.deleteIfExists(path);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex != -1 ? filename.substring(dotIndex) : "";
    }

}
