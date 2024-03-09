package gov.iti.jets.testing.infrastructure.persistence;

import gov.iti.jets.testing.domain.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;

public class UserDao {
    public static Optional<User> findUserById(Long userId, EntityManager em) {
        return Optional.ofNullable(em.find(User.class, userId));
    }

    public static User save(User user, EntityManager em) {
        em.persist(user);
        return user;
    }
}
