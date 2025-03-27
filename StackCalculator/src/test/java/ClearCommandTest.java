import org.example.calculatorSettings.Calculator;
import org.example.calculatorSettings.CalculatorException;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ClearCommandTest {

    @Test
    void testClearCommandTrue() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "PUSH 20",
                "CLEAR"
        );
        calculator.executeCommands(commands);
        assertTrue(calculator.getContext().getStack().isEmpty(), "Stack should be empty after CLEAR command");
    }
}