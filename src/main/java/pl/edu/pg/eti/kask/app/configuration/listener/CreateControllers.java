package pl.edu.pg.eti.kask.app.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.app.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.app.user.controller.implementation.UserControllerImpl;
import pl.edu.pg.eti.kask.app.user.service.implementation.UserServiceImpl;
import pl.edu.pg.eti.kask.app.user.service.api.UserService;

@WebListener
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UserService userService = (UserServiceImpl) event.getServletContext().getAttribute("userService");

        event.getServletContext().setAttribute("userController", new UserControllerImpl(
                userService,
                new DtoFunctionFactory()
        ));

    }
}

