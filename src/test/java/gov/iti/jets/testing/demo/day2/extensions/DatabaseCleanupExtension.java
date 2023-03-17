package gov.iti.jets.testing.demo.day2.extensions;

import gov.iti.jets.testing.day2.shopping.infrastructure.persistence.Database;
import jakarta.persistence.Query;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

public class DatabaseCleanupExtension implements BeforeEachCallback {

    public static final List<String> DELETE_STATEMENTS =
            List.of("DELETE FROM users;",
                    "DELETE FROM products;",
                    "DELETE FROM order_line_items;",
                    "DELETE FROM orders;");

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        clearDatabase();
    }

    private static void clearDatabase() {
        Database.doInTransactionWithoutResult(
                em -> DELETE_STATEMENTS.stream()
                                       .map(em::createNativeQuery)
                                       .forEach(Query::executeUpdate)
        );
    }
}
