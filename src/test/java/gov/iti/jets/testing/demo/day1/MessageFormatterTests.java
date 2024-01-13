package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.domain.MessageFormatter;
import org.approvaltests.Approvals;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertionsProvider;
import org.assertj.core.api.SoftAssertionsProvider.ThrowingRunnable;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.api.ThrowingConsumer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.List;

@Fast
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MessageFormatterTests {

    // MessageFormatter

    // MessageFormatterTests
    // MessageFormatter_FormatTests

//    MessageFormatter messageFormatter;

//    @BeforeEach
//    void init() {
//        messageFormatter = new MessageFormatter();
//    }

    @Test
    //    v v v v v // coupled to implementation
    @DisplayName("""
            Given a message
            When message formatter receives the messages
            Then it is added to its messages list
            """)
    @Disabled("No longer needed")
    void Add_message_accepts_a_message() {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter();
        String expectedMessage = "Hello";

        // Act
        messageFormatter.addMessage(expectedMessage);

        // Assert
        // Junit vs AssertJ
//        String actualMessage = messageFormatter.getMessagesUnmodifiable().get(0);
//        Assertions.assertEquals(expectedMessage, actualMessage);
        Assertions.assertThat(messageFormatter.getMessagesUnmodifiable())
                .containsExactly(expectedMessage);
    }

    private static MessageFormatter createMessageFormatter() {
        MessageFormatter messageFormatter = new MessageFormatter();
        return messageFormatter;
    }

    // TODO 001
    @Test
    void Null_messages_are_not_allowed() {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter();


        Assertions.assertThatException()
                // Act
                .isThrownBy(() -> messageFormatter.addMessage(null))
                // Assert
                .isInstanceOf(IllegalArgumentException.class)
                .withMessage("Null messages are not allowed");

    }



    @Test
    void Adds_two_integers() {
        // Arrange
        int a = 1;
        int b = 2;

        // Act
        int result = add(a, b);

        // Assert
        Assertions.assertThat(result)
                .isEqualTo(3);
    }



    static int add(int a, int b) {
        return a + b;
    }

    // TODO 001
    @Test
    void Formats_asterisk_message_to_uppercase(@TempDir File file) {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter();
        messageFormatter.addMessage("*hello");

        // Act
        String actual = messageFormatter.formatMessages();

        // Assert
        Assertions.assertThat(actual)
                .isEqualTo("HELLO");
    }

    @CsvSource(
            textBlock = """
                    *hello, HELLO
                    *world, WORLD
                    *JeTs, JETS
                    """
    )
    @ParameterizedTest // TODO 002
    void Formats_asterisk_messages_to_uppercase(String message, String expected) {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter();
        messageFormatter.addMessage(message);

        // Act
        String actual = messageFormatter.formatMessages();

        // Assert
        Assertions.assertThat(actual)
                .isEqualTo(expected);
    }


    // TODO 008 Mockito introduction
    @Test
    void Stores_a_message() {

    }


    @Test
    void Dependency_injection(TestInfo testInfo) {
        System.out.println(testInfo.getTestClass());
        System.out.println(testInfo.getTestMethod());
        System.out.println(testInfo.getDisplayName());
    }

    @Test
    void Add_message_accepts_a_message_using_approval_testing() {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter();
        String expectedMessage = "Hello";

        // Act
        messageFormatter.addMessage("hamada");

        // Assert
        // Characterization test
        Approvals.verify(messageFormatter.getMessagesUnmodifiable());
    }
}
