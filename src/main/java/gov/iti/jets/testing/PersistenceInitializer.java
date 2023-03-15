package gov.iti.jets.testing;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class PersistenceInitializer implements ServletContextListener {

    @Override
    public void contextInitialized( ServletContextEvent sce ) {
        sce.getServletContext().setAttribute( "emf", Persistence.createEntityManagerFactory("testing_course") );
    }

    @Override
    public void contextDestroyed( ServletContextEvent sce ) {
        ((EntityManagerFactory) sce.getServletContext().getAttribute( "emf" )).close();
        System.out.println( "Context destroyed" );
    }
}
