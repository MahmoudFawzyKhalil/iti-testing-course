package gov.iti.jets.testing.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// TODO write test
public class MessageFormatter {
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public String formatMessages() {
        return messages.stream()
                       .map(this::formatMessage)
                       .collect(Collectors.joining("\n"));
    }

    private String formatMessage(String message) {
        if (message.startsWith("*")) {
            return message.toUpperCase();
        } else if (message.startsWith("_")) {
            return message.toLowerCase();
        } else {
            return message;
        }
    }

    // Ignore this comment
    // Refactoring this is a pain because what if we want to disallow repeated messages and change it to a set?
    // Verifying unnecessarily the internal state
    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}
