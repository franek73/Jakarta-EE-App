package pl.edu.pg.eti.kask.app.category.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.app.category.dto.GetCategoriesResponse;
import pl.edu.pg.eti.kask.app.category.dto.GetCategoryResponse;
import pl.edu.pg.eti.kask.app.category.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.app.category.dto.PutCategoryRequest;

import java.util.UUID;

@Path("")
public interface CategoryController {

    @GET
    @Path("/categories")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoriesResponse getCategories();

    @GET
    @Path("/categories/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetCategoryResponse getCategory(@PathParam("id") UUID id);

    @DELETE
    @Path("/categories/{id}")
    void deleteCategory(@PathParam("id") UUID id);

    @PUT
    @Path("/categories/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putCategory(@PathParam("id") UUID id, PutCategoryRequest request);

    @PATCH
    @Path("/categories/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchCategory(@PathParam("id") UUID id, PatchCategoryRequest request);
}