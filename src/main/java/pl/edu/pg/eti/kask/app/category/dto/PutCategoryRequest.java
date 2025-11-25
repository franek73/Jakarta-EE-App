package pl.edu.pg.eti.kask.app.category.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutCategoryRequest {

    private String name;

    private String description;
}
