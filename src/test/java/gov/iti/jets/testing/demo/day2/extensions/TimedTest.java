package gov.iti.jets.testing.demo.day2.extensions;


import org.junit.jupiter.api.Test;

@Timed
class TimedTest {
    @Test
    void timedTest() throws Exception {
        Thread.sleep(1000);
    }
}
