package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.domain.MessageFormatter;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Fast
class MessageFormatterTests_ {

    static Stream<Arguments> messagesProvider() {
        return Stream.of(
                Arguments.of("*hello", "HELLO"),
                Arguments.of("*", ""),
                Arguments.of("*Hello\n", "HELLO\n")
        );
    }

    @BeforeEach
    void doSomething() {
        System.out.println("Starting a test...");
    }

    @ParameterizedTest
    @MethodSource("messagesProvider")
    @Disabled("In the next release")
    void Formats_asterisk_message_to_uppercase(String message, String expected) {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter(message);

        // Act
        String formattedMessages = messageFormatter.formatMessages();

        // Assert
        assertThat(formattedMessages)
                .isEqualTo(expected);
    }

    // TODO 005 few lines of code
    private static MessageFormatter createMessageFormatter(String message) {
        MessageFormatter messageFormatter = new MessageFormatter();
        messageFormatter.addMessage(message);
        return messageFormatter;
    }

    @Test
    void Null_messages_are_not_allowed() {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter(null);

        // Act
        ThrowableAssert.ThrowingCallable formatMessagesCall =
                messageFormatter::formatMessages;

        // Assert
        Assertions.assertThatThrownBy(formatMessagesCall)
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void Stores_a_message() {
        // 1. Resistance to refactoring [bad] -> OVERSPECIFICATION
        // 2. Protection against regressions
        // 3. Maintainability [average]
        // 4. Speed [good]

        // Arrange
        // Collaboration verification
        // MessageFormatter -> A calls B with the right arguments
        // List<String> -> B
//        ArrayList<String> listMock = Mockito.mock();
//        MessageFormatter messageFormatter = new MessageFormatter(listMock);

        // Act
//        messageFormatter.addMessage("Hello");

        // Assert
        // Make sure A calls B's add method with the string "Hello"
//        Mockito.verify(listMock).add("Hello");
        // ALWAYS verify no more interactions after verifying the ones you care about
//        Mockito.verifyNoMoreInteractions(listMock);
    }

}
