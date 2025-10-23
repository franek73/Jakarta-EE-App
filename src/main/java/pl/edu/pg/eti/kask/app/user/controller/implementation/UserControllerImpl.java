package pl.edu.pg.eti.kask.app.user.controller.implementation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.user.controller.api.UserController;
import pl.edu.pg.eti.kask.app.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.app.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.app.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.app.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.app.user.service.api.UserService;
import pl.edu.pg.eti.kask.app.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.app.controller.servlet.exception.NotFoundException;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class UserControllerImpl implements UserController {

    private final UserService service;

    private final DtoFunctionFactory factory;

    @Inject
    public UserControllerImpl(UserService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
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
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
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
