package gov.iti.jets.testing.day2.shopping.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.function.Consumer;
import java.util.function.Function;

public class Database {
    private Database() {
    }

    // Spring's TransactionCallBack
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("testing_course");

    public static <R> R doInTransaction(
            Function<EntityManager, R> returningTransactionFunction) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            R result = returningTransactionFunction.apply(entityManager);
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void doInTransactionWithoutResult(
            Consumer<EntityManager> voidTransactionFunction) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            voidTransactionFunction.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void close() {
        emf.close();
        System.out.println("Database resources cleaned up");
    }
}
