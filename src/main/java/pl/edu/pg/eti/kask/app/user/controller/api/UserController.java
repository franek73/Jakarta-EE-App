package pl.edu.pg.eti.kask.app.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.app.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.app.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.app.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.app.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Path("")
public interface UserController {
    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void putUser(@PathParam("id") UUID id, PutUserRequest user);

    @PATCH
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    void patchUser(@PathParam("id") UUID id, PatchUserRequest user);

    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);

    Optional<byte[]> getUserAvatar(UUID id);

    void putUserAvatar(UUID id, InputStream avatar, String originalFilename);

    void patchUserAvatar(UUID id, InputStream avatar, String originalFilename);

    void deleteUserAvatar(UUID id);
}
