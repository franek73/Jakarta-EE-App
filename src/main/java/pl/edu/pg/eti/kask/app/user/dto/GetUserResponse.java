package pl.edu.pg.eti.kask.app.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class GetUserResponse {

    private UUID id;

    private String login;

    private String name;

    private String email;

    private LocalDate registrationDate;
}
