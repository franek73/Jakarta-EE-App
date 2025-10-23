package pl.edu.pg.eti.kask.app.recipe.respository.api;

import pl.edu.pg.eti.kask.app.recipe.entity.Category;
import pl.edu.pg.eti.kask.app.repository.api.Repository;

import java.util.UUID;

public interface CategoryRepository extends Repository<Category, UUID> {
}
