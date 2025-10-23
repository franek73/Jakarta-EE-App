package pl.edu.pg.eti.kask.app.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pl.edu.pg.eti.kask.app.recipe.controller.api.CategoryController;
import pl.edu.pg.eti.kask.app.recipe.controller.api.RecipeController;
import pl.edu.pg.eti.kask.app.recipe.dto.PatchRecipeRequest;
import pl.edu.pg.eti.kask.app.recipe.dto.PutRecipeRequest;
import pl.edu.pg.eti.kask.app.user.controller.api.UserController;
import pl.edu.pg.eti.kask.app.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.app.user.dto.PutUserRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private final UserController userController;

    private final RecipeController recipeController;

    private final CategoryController categoryController;

    public static final class Paths {

        public static final String API = "/api";
    }

    public static final class Patterns {

        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern RECIPES = Pattern.compile("/recipes/?");

        public static final Pattern RECIPE = Pattern.compile("/recipes/(%s)".formatted(UUID.pattern()));

        public static final Pattern CATEGORIES = Pattern.compile("/categories/?");

        public static final Pattern CATEGORY = Pattern.compile("/categories/(%s)".formatted(UUID.pattern()));

        public static final Pattern CATEGORY_RECIPES = Pattern.compile("/categories/(%s)/recipes/?".formatted(UUID.pattern()));

        public static final Pattern USER_RECIPES = Pattern.compile("/users/(%s)/recipes/?".formatted(UUID.pattern()));

        public static final Pattern USERS = Pattern.compile("/users/?");

        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(UserController userController, RecipeController recipeController, CategoryController categoryController) {
        this.userController = userController;
        this.recipeController = recipeController;
        this.categoryController = categoryController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RECIPES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(recipeController.getRecipes()));
                return;
            } else if (path.matches(Patterns.RECIPE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.RECIPE, path);
                response.getWriter().write(jsonb.toJson(recipeController.getRecipe(uuid)));
                return;
            } else if (path.matches(Patterns.CATEGORIES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(categoryController.getCategories()));
                return;
            } else if (path.matches(Patterns.CATEGORY.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.CATEGORY, path);
                response.getWriter().write(jsonb.toJson(categoryController.getCategory(uuid)));
                return;
            } else if (path.matches(Patterns.CATEGORY_RECIPES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.CATEGORY_RECIPES, path);
                response.getWriter().write(jsonb.toJson(recipeController.getCategoryRecipes(uuid)));
                return;
            } else if (path.matches(Patterns.USER_RECIPES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_RECIPES, path);
                response.getWriter().write(jsonb.toJson(recipeController.getUserRecipes(uuid)));
                return;
            } else if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.getUserAvatar(uuid)
                        .ifPresentOrElse(
                                data -> {
                                    try {
                                        response.setContentType("image/png");
                                        response.setContentLength(data.length);
                                        response.getOutputStream().write(data);
                                        response.setStatus(HttpServletResponse.SC_OK);
                                    } catch (IOException e) {
                                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                    }
                                },
                                () -> response.setStatus(HttpServletResponse.SC_NOT_FOUND)
                        );
                return;
            }

        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RECIPE.pattern())) {
                UUID uuid = extractUuid(Patterns.RECIPE, path);
                recipeController.putRecipe(uuid, jsonb.fromJson(request.getReader(), PutRecipeRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "recipes", uuid.toString()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.putUser(uuid, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "users", uuid.toString()));
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                Part avatarPart = request.getPart("avatar");
                String originalName = java.nio.file.Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
                userController.putUserAvatar(uuid, avatarPart.getInputStream(), originalName);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RECIPE.pattern())) {
                UUID uuid = extractUuid(Patterns.RECIPE, path);
                recipeController.deleteRecipe(uuid);
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.deleteUser(uuid);
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                userController.getUserAvatar(uuid)
                        .ifPresentOrElse(
                                data -> {
                                    userController.deleteUserAvatar(uuid);
                                    response.setStatus(HttpServletResponse.SC_OK);
                                },
                                () -> response.setStatus(HttpServletResponse.SC_NO_CONTENT)
                        );
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.RECIPE.pattern())) {
                UUID uuid = extractUuid(Patterns.RECIPE, path);
                recipeController.patchRecipe(uuid, jsonb.fromJson(request.getReader(), PatchRecipeRequest.class));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                userController.patchUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                Part avatarPart = request.getPart("avatar");
                String originalName = java.nio.file.Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
                userController.patchUserAvatar(uuid, avatarPart.getInputStream(), originalName);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}

