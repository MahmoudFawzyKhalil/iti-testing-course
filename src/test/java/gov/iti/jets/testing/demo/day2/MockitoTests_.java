package gov.iti.jets.testing.demo.day2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MockitoTests_ {
    @Test
    void Create_user() {
        // User.create is not a pure function, because it has hidden inputs

        MockedStatic<LocalDateTime> localDateTime =
                Mockito.mockStatic(LocalDateTime.class);

        LocalDateTime stubbedTime =
                LocalDateTime.of(2020, 1, 1, 1, 1);


        // Testable
        // Dependency injection
        // OrderService() -> ShoppingService, LocalDateTime.now(), X, Y, Z
        // Polymorphism
        // Instantiate OrderService ->

//        LocalDateTime.now( Clock.fixed() )

        localDateTime.when(LocalDateTime::now)
                .thenReturn(stubbedTime);

        System.out.println(LocalDateTime.now());

        LocalDateTime creationDate = LocalDateTime.now();
        User user = User.create("Mahmoud   ");

        Assertions.assertThat(user.name)
                .isEqualTo("Mahmoud");

        // False positive
        Assertions.assertThat(user.createdAt)
                .isEqualTo(stubbedTime);

        localDateTime.close();
    }

    // Calling another function/method
    static class User {
        private String name;
        private LocalDateTime createdAt;

        private User(String name) {
            this.name = name;
        }

        public static User create(String name) {
            User user = new User(name.strip());
            user.createdAt = LocalDateTime.now();
            return user;
        }
    }
}
