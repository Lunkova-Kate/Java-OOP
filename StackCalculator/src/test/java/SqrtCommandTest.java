import org.example.calculatorSettings.Calculator;
import org.example.calculatorSettings.CalculatorException;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SqrtCommandTest {

    @Test
    void testSqrtCommandSuccessEquals() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 16",
                "SQRT"
        );
        calculator.executeCommands(commands);
        assertEquals(4.0, calculator.getContext().getStack().peek(), "Square root of 16 should be 4.0");
    }

    @Test
    void testSqrtCommandNegativeNumberThrows() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH -10",
                "SQRT"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "Square root of negative number should throw an exception");
    }

    @Test
    void testSqrtCommandEmptyStackThrows() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "SQRT"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "SQRT on empty stack should throw an exception");
    }
}