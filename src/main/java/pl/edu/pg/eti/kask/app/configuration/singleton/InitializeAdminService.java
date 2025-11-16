package pl.edu.pg.eti.kask.app.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.entity.UserRole;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;

import java.time.LocalDate;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final UserRepository userRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userRepository.findByLogin("admin-service").isEmpty()) {

            User admin = User.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .name("Admin")
                    .email("admin-service@gmail.com")
                    .password(passwordHash.generate("adminservice".toCharArray()))
                    .registrationDate(LocalDate.now())
                    .role(UserRole.ADMIN)
                    .build();

            userRepository.create(admin);
        }
    }

}
