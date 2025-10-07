package pl.edu.pg.eti.kask.app.composition;

import java.time.LocalDate;

import lombok.*;
import pl.edu.pg.eti.kask.app.genre.Genre;
import pl.edu.pg.eti.kask.app.user.*;

@Data
public class Composition {

    private String title;

    private LocalDate creationDate;

    private String description;

    private Difficulty difficulty;

    private Genre genre;

    private User author;
}
