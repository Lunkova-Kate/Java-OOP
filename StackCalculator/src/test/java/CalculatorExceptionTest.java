import org.example.calculatorSettings.CalculatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorExceptionTest {

    @Test
    void testConstructorWithMessageErrorEquals() {
        String errorMessage = "Test error message";
        CalculatorException exception = new CalculatorException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());

        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageEquals() {

        String errorMessage = "Test error message";
        Throwable cause = new RuntimeException("Root cause");
        CalculatorException exception = new CalculatorException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());

        assertNotNull(exception.getCause());
        assertEquals(cause, exception.getCause());
        assertEquals("Root cause", exception.getCause().getMessage());
    }
}