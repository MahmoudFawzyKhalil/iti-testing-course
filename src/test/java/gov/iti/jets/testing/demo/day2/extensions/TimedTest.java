package gov.iti.jets.testing.demo.day2.extensions;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@Timed
class TimedTest {
    @Test
    void timedTest() throws Exception {
        Thread.sleep(1000);
    }

    @Test
    void thisShouldNotTakeVeryLong() {
        System.out.println("hello");
    }
}
