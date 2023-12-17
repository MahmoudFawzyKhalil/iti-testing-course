package gov.iti.jets.testing.demo.day2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

// TODO 009
@ExtendWith(MockitoExtension.class)
public class MockitoTests {
    @Test
    void Create_user() {

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


    // TODO 008
    @Test
    void Example_mockito_test() {
        // Arrange
        List<String> listMock = Mockito.mock();

        Mockito.when(listMock.get(0))
                .thenReturn("ITI 43");

        ListPrinter listPrinter = new ListPrinter(listMock);

        // Act
        listPrinter.printFirstElement();

        // Assert
        Mockito.verify(listMock).get(0);
        Mockito.verifyNoMoreInteractions(listMock); // Important!
    }


    static class ListPrinter {
        private final List<String> list;

        ListPrinter(List<String> list) {
            this.list = list;
        }

        public void printFirstElement() {
            System.out.println(list.get(0));
        }
    }

}
