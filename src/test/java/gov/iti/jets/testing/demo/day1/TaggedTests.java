package gov.iti.jets.testing.demo.day1;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TaggedTests {

    @Test
    @Tag("fast")
    void fastTest() {
        System.out.println("Fast test");
    }

    @FastTest
    void metaAnnotatedFastTest() {
        System.out.println("Meta annotated fast test");
    }
}
