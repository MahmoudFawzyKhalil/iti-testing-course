package gov.iti.jets.testing.demo;


import org.junit.jupiter.api.Test;

@Timed
class TimedTest {
    @Test
    void timedTest() throws Exception {
        Thread.sleep(1000);
    }
}
