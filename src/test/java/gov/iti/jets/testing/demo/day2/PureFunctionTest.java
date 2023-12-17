package gov.iti.jets.testing.demo.day2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// TODO 006
public class PureFunctionTest {
    // Pure function -> mathematical function
    // f(x,y) = x + y
    // No side effects
    // Deterministic
    // No hidden inputs
    // No hidden outputs

    @Test
    void Divide_by_zero() {
        int result = divide(5, 0);
    }

    int divide(int a, int b) {
        return a / b;
    }

    // No hidden inputs
    // Hidden outputs: either returns an int result, OR throws an exception
    // Vavr
    // Either<Integer, Exception>
    public Optional<Integer> dividePure(int a, int b) {
        Integer result = null;

        try {
            result = a / b;
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(result);
    }
}
