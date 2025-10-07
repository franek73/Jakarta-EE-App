package pl.edu.pg.eti.kask.app.user;

import lombok.*;
import pl.edu.pg.eti.kask.app.composition.Composition;

import java.util.List;

@Data
public class User {

    private int id;

    private String name;

    private Role role;

    List<Composition> userCompositions;
}
