package gov.iti.jets.testing.demo.day1;

import gov.iti.jets.testing.day1.MessageFormatter;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Fast
class MessageFormatterTests {

    @BeforeEach
    void doSomething() {
        System.out.println( "Starting tests!!!!!!" );
    }

    @ParameterizedTest
    @MethodSource("messagesProvider")
    @Disabled("In the next release")
    void Formats_asterisk_message_to_uppercase( String message, String expected ) {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter(message );

        // Act
        String formattedMessages = messageFormatter.formatMessages();

        // Assert
        assertThat( formattedMessages )
                .isEqualTo( expected );
    }

    private static MessageFormatter createMessageFormatter( String message ) {
        MessageFormatter messageFormatter = new MessageFormatter(new ArrayList<>() );
        messageFormatter.addMessage( message );
        return messageFormatter;
    }

    @Test
    void Null_messages_are_not_allowed() {
        // Arrange
        MessageFormatter messageFormatter = createMessageFormatter( null );

        // Act
        ThrowableAssert.ThrowingCallable formatMessagesCall =
                messageFormatter::formatMessages;

        // Assert
        Assertions.assertThatThrownBy( formatMessagesCall )
                .isInstanceOf( NullPointerException.class );
    }

    public static Stream<Arguments> messagesProvider() {
        return Stream.of(
                Arguments.of( "*hello", "HELLO" ),
                Arguments.of( "*", "" ),
                Arguments.of( "*Hello\n", "HELLO\n" )
        );
    }

}
