package gov.iti.jets.testing.demo.day1;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Tag( "fast" ) // Metannotation
public @interface Fast { // annotation
}
