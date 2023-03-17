package gov.iti.jets.testing.demo.day1;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class AssertJTests {
    @Test
    void fluentDsl() {
        String stringResult = "ITI";
        Assertions.assertThat( stringResult )
                .isGreaterThan( "I" );
    }

    @Test
    void throwsException() {
        Assertions.assertThatThrownBy( () -> {
            int i = Integer.parseInt( "Definitely not an integer" );
        } ).isInstanceOf( NumberFormatException.class );
    }


    @Test
    void youDontHaveEquals() {
        Person actual = new Person( "Mahmoud", "mahmoud@gmail.com" );
        Person expected = new Person( "Mahmoud", "mahmoud@gmail.com" );
//
//        Assertions.assertThat( actual.name() )
//                .isEqualTo( expected.name );
//
//        Assertions.assertThat( actual.email() )
//                .isEqualTo( expected.email() );

        Assertions.assertThat( actual )
                .usingRecursiveComparison()
                .isEqualTo( expected );
    }

    @Test
    void regularAssertions() {
        // reports first failure
        Assertions.assertThat( "iti" )
                .hasSizeLessThan( 1 );

        Assertions.assertThat( "iti" )
                .isUpperCase();
    }

    @Test
    void softAssertions() {
        try (var softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat( "iti" )
                    .hasSizeLessThan( 1 );

            softly.assertThat( "iti" )
                    .isUpperCase();
        }
    }

    @Test
    void softAssertions2() {
        var softly = new SoftAssertions();
        softly.assertThat( "iti" )
                .hasSizeLessThan( 1 );

        softly.assertThat( "iti" )
                .isUpperCase();

        softly.assertAll();
    }


    record Person(String name, String email) {
    }
}
