package pl.edu.pg.eti.kask.app.user.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {
    private UserRepository repository;

    private final String avatarDir;

    @Inject
    public UserService(UserRepository repository) {
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

    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public void create(User entity) {
        repository.create(entity);
    }

    public void update(User entity) {
        repository.update(entity);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

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
