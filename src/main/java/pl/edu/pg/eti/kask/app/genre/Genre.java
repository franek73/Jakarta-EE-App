package pl.edu.pg.eti.kask.app.genre;

import lombok.*;
import pl.edu.pg.eti.kask.app.composition.Composition;

import java.util.List;

@Data
public class Genre {
    private String name;

    private String description;

    private List<Composition> compositions;
}