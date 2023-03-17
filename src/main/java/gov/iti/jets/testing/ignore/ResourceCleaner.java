package gov.iti.jets.testing.ignore;

import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ResourceCleaner implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Database.close();
    }
}
