package gov.iti.jets.testing.day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// TODO write test
public class MessageFormatter {
    private List<String> messages;

    public MessageFormatter( List<String> messages ) {
        this.messages = messages;
    }

    public void addMessage( String message ) {
        messages.add( message );
        System.out.println("Added message: " + messages.get( 0 ));
        // REFACTORING
    }

    public String formatMessages() {
        String formatted = messages.stream()
                .map( this::formatMessage )
                .collect( Collectors.joining( "\n" ) );

        messages.clear();

        return formatted;
    }

    private String formatMessage( String message ) {
        if (message.startsWith( "*" )) {
            return message.replace( "*", "" ).toUpperCase();
        } else if (message.startsWith( "_" )) {
            return message.replace( "_", "" ).toLowerCase();
        } else {
            return message;
        }
    }

    // Ignore this comment
    // Refactoring this is a pain because what if we want to disallow repeated messages and change it to a set?
    // Verifying unnecessarily the internal state

    // The principle of least astonishment
    public List<String> getMessagesUnmodifiable() {
        return Collections.unmodifiableList( messages );
    }
}
