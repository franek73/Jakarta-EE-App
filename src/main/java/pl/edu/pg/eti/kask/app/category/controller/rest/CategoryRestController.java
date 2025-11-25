package pl.edu.pg.eti.kask.app.category.controller.rest;

import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.app.category.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.app.category.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.app.category.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.app.category.dto.PutCategoryRequest;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.category.controller.api.CategoryController;
import pl.edu.pg.eti.kask.app.category.service.CategoryService;

import java.util.UUID;

@Path("")
public class CategoryRestController implements CategoryController {

    private CategoryService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public CategoryRestController(DtoFunctionFactory factory,
                                  @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(CategoryService service) {
        this.service = service;
    }

    @Override
    public GetCategoriesResponse getCategories() {
        return factory.categoriesToResponse().apply(service.findAll());
    }

    @Override
    public GetCategoryResponse getCategory(UUID id) {
        return service.find(id)
                .map(factory.categoryToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteCategory(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    @SneakyThrows
    public void putCategory(UUID id, PutCategoryRequest request) {
        try {
            service.create(factory.requestToCategory().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(CategoryController.class, "getCategory")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchCategory(UUID id, PatchCategoryRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateCategory().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}