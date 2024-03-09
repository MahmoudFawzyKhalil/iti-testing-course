package gov.iti.jets.testing.demo.day2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockVsFake {

    static class ItemService {
        private final ExternalItemService externalItemService;

        private Clock clock;

        public ItemService(ExternalItemService externalItemService) {
            this.externalItemService = externalItemService;
        }

        public ItemService(ExternalItemService externalItemService, Clock clock) {
            this.externalItemService = externalItemService;
            this.clock = clock;
        }

        String getItem(Long id, LocalDateTime currentDateTime) {
            String item = externalItemService.getItem(id);
            externalItemService.insertLogMessage("Item accessed. itemId=%s, timestamp=%s".formatted(
                    id,
                    currentDateTime
            ));
            return item; // Postfix completion
        }

        String getItem2(Long id) {
            String item = externalItemService.getItem(id);
            externalItemService.insertLogMessage("Item accessed. itemId=%s, timestamp=%s".formatted(
                    id,
                    LocalDateTime.now(clock)
            ));
            return item; // Postfix completion
        }
    }

    static interface ExternalItemService {
        void insertItem(Long id, String item);
        String getItem(Long id);

        void insertLogMessage(String message);
        String getLatestLogMessage();
    }

    @Test
    void Retrieves_an_item_by_id_inline_mock() {
        // Arrange
        // Inline mock
        ExternalItemService mock = Mockito.mock(ExternalItemService.class);
        long itemId = 1L;
        Mockito.when(mock.getItem(itemId)).thenReturn("Mahmoud");

        ItemService itemService = new ItemService(mock);

        // Act
        String item = itemService.getItem(itemId, LocalDateTime.now());

        // Assert
        Assertions.assertThat(item)
                .isEqualTo("Mahmoud");

        Mockito.verify(mock)
                .getItem(itemId);
        Mockito.verify(mock)
                .insertLogMessage(Mockito.any());

        Mockito.verifyNoMoreInteractions(mock);
    }

    @Test
    void Retrieves_an_item_by_id_fake() {
        // Arrange
        ExternalItemService fake = new FakeExternalItemService();

        ItemService itemService = new ItemService(fake);
        fake.insertItem(1L, "Mahmoud");

        LocalDateTime currentDateTime = LocalDateTime.of(2020, 01, 01, 01, 01);

        // Act
        String item = itemService.getItem(1L, currentDateTime);

        // Assert
        Assertions.assertThat(item)
                .isEqualTo("Mahmoud");

        Assertions.assertThat(fake.getLatestLogMessage())
                .contains("Item accessed.")
                .contains(currentDateTime.toString());
    }

    static class FakeExternalItemService implements ExternalItemService {
        private final Map<Long, String> items = new HashMap<>();
        private final List<String> logMessages = new ArrayList<>();

        @Override
        public void insertItem(Long id, String item) {
            items.put(id, item);
        }

        @Override
        public String getItem(Long id) {
            return items.get(id);
        }

        @Override
        public void insertLogMessage(String message) {
            logMessages.add(message);
        }

        @Override
        public String getLatestLogMessage() {
            return logMessages.get(logMessages.size() - 1);
        }
    }
}
