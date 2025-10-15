package pl.edu.pg.eti.kask.app.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.app.datastore.component.DataStore;
import pl.edu.pg.eti.kask.app.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.app.user.repository.memory.UserInMemoryRepository;
import pl.edu.pg.eti.kask.app.user.service.implementation.UserServiceImpl;

@WebListener
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);

        String avatarDir = event.getServletContext().getInitParameter("avatarDir");

        event.getServletContext().setAttribute("userService", new UserServiceImpl(userRepository, avatarDir));
    }

}


