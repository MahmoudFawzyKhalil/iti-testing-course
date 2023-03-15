package gov.iti.jets.testing.demo;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE, METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@ExtendWith(TimerExtension.class)
public @interface Timed { }
