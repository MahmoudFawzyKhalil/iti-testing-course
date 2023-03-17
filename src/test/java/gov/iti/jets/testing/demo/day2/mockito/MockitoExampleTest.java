package gov.iti.jets.testing.demo.day2.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MockitoExampleTest {

    @Test
    void test() {
        // Arrange
        List<String> listMock = Mockito.mock(); // Convert to annotated mock

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
