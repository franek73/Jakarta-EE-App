package pl.edu.pg.eti.kask.app.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.app.user.entity.Role;
import pl.edu.pg.eti.kask.app.user.entity.User;
import pl.edu.pg.eti.kask.app.user.service.api.UserService;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;
import lombok.SneakyThrows;

@WebListener
public class InitializedData implements ServletContextListener {

    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        userService = (UserService) event.getServletContext().getAttribute("userService");
        init();
    }

    @SneakyThrows
    private void init() {
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .email("admin@gmail.com")
                .name("Admin")
                .password("admin123")
                .registeredDate(LocalDate.now())
                .role(Role.Admin)
                .build();

        User franek = User.builder()
                .id(UUID.fromString("81e1c2a9-7f57-439b-b53d-6db88b071e4e"))
                .login("franek73")
                .email("franek@gmail.com")
                .name("Franek")
                .password("franek123")
                .registeredDate(LocalDate.now())
                .role(Role.User)
                .build();

        User maciek = User.builder()
                .id(UUID.fromString("ed6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("maniek")
                .email("maciek@gmail.com")
                .name("Maciek")
                .password("maciek123")
                .registeredDate(LocalDate.now())
                .role(Role.User)
                .build();

        User wilk = User.builder()
                .id(UUID.fromString("fd6cfb2a-cad7-47dd-9b56-9d1e3c7a4197"))
                .login("wilk")
                .email("wilk@gmail.com")
                .name("Wilk")
                .password("wilk123")
                .registeredDate(LocalDate.now())
                .role(Role.User)
                .build();

        userService.create(admin);
        userService.create(franek);
        userService.create(maciek);
        userService.create(wilk);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }

}
