package pl.edu.pg.eti.kask.app.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class GetUsersResponse {

    @Data
    @Builder
    public static class User {
        private UUID id;

        private String login;

        private String name;

        private String email;

        private LocalDate registrationDate;
    }

    @Singular
    private List<User> users;

}
