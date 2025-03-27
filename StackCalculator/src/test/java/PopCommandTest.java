import org.example.calculatorSettings.Calculator;
import org.example.calculatorSettings.CalculatorException;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PopCommandTest {

    @Test
    void testPopCommandSuccessTrue() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "POP"
        );
        calculator.executeCommands(commands);
        assertTrue(calculator.getContext().getStack().isEmpty(), "Stack should be empty after POP command");
    }

    @Test
    void testPopCommandEmptyStackThrows() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "POP"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "POP on empty stack should throw an exception");
    }
}