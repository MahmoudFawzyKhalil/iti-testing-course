package gov.iti.jets.testing.demo;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.Test;

class AssertJTests {
    @Test
    void fluentDsl() {
        String stringResult = "ITI";
        Assertions.assertThat(stringResult)
                  .isGreaterThan("I");
    }

    @Test
    void throwsException() {
        Assertions.assertThatThrownBy(() -> {
            int i = Integer.parseInt("Definitely not an integer");
        }).isInstanceOf(NumberFormatException.class);
    }


    @Test
    void youDontHaveEquals() {
        Person person = new Person("Mahmoud", "mahmoud@gmail.com");
        Assertions.assertThat(person)
                  .usingRecursiveComparison()
                  .isEqualTo(new Person("Mahmoud", "mahmoud@gmail.com"));
    }

    @Test
    void regularAssertions() {
        // reports first failure
        Assertions.assertThat("iti")
                  .hasSizeLessThan(1);

        Assertions.assertThat("iti")
                  .isUpperCase();
    }

    @Test
    void softAssertions() {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat("iti")
                  .hasSizeLessThan(1);

            softly.assertThat("iti")
                  .isUpperCase();
        }
    }


    record Person(String name, String email) {
    }
}
