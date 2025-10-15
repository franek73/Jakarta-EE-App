package pl.edu.pg.eti.kask.app.user.dto;

import lombok.Data;

@Data
public class PatchUserRequest {

    private String login;

    private String name;

    private String email;
}
