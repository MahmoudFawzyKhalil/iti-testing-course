package gov.iti.jets.testing.demo.day2.extensions;

import gov.iti.jets.testing.infrastructure.persistence.Database;
import jakarta.persistence.Query;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

public class DatabaseCleanupExtension implements BeforeEachCallback, ExtensionContext.Store.CloseableResource {

    static boolean registered = false;

    public static final List<String> DELETE_STATEMENTS =
            List.of("DELETE FROM users;",
                    "DELETE FROM products;",
                    "DELETE FROM order_line_items;",
                    "DELETE FROM orders;");

    @Override
    public void beforeEach(ExtensionContext context) {
        clearDatabase();
        if (!registered) {
            context.getRoot()
                    .getStore(ExtensionContext.Namespace.GLOBAL)
                    .put(DatabaseCleanupExtension.class.getName(), this);
            registered = true;
        }
    }

    private static void clearDatabase() {
        Database.doInTransactionWithoutResult(
                em -> DELETE_STATEMENTS.stream()
                        .map(em::createNativeQuery)
                        .forEach(Query::executeUpdate)
        );
    }

    @Override
    public void close() {
        // Runs after ALL tests in test suite run
        Database.close();
    }
}
