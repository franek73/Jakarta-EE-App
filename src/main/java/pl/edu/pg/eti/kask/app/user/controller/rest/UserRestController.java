package pl.edu.pg.eti.kask.app.user.controller.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.recipe.controller.api.RecipeController;
import pl.edu.pg.eti.kask.app.recipe.service.api.CategoryService;
import pl.edu.pg.eti.kask.app.user.controller.api.UserController;
import pl.edu.pg.eti.kask.app.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.app.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.app.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.app.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.app.user.service.api.UserService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
public class UserRestController implements UserController {

    private final UserService service;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public UserRestController(UserService service, DtoFunctionFactory factory,
                              @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.service = service;
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @Override
    public GetUsersResponse getUsers() {
        return factory.usersToResponse().apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return service.find(id)
                .map(factory.userToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUser(UUID id, PutUserRequest request) {
        try {
            service.create(factory.requestToUser().apply(id, request));

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(UserController.class, "getUser")
                    .build(id)
                    .toString());

            throw new WebApplicationException(Response.Status.CREATED);
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException(ex);
            }
            throw ex;
        }
    }

    @Override
    public void patchUser(UUID id, PatchUserRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateUser().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUser(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public Optional<byte[]> getUserAvatar(UUID id) {
        return service.findAvatar(id);
    }

    @Override
    public void putUserAvatar(UUID id, InputStream avatar, String originalFilename) {
        service.find(id).ifPresentOrElse(
                entity -> service.createAvatar(id, avatar, originalFilename),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void patchUserAvatar(UUID id, InputStream avatar, String originalFilename) {
        service.find(id).ifPresentOrElse(
                entity -> service.updateAvatar(id, avatar, originalFilename),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    public void deleteUserAvatar(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.deleteAvatar(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }


}
