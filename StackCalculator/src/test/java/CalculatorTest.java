import org.example.calculatorSettings.Calculator;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.ExecutionContext;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testExecuteCommandsEqualsEquals() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "DEFINE A 10",
                "PUSH A",
                "PUSH 20",
                "ADD",
                "PRINT"
        );
        calculator.executeCommands(commands);
        ExecutionContext context = calculator.getContext();
        assertEquals(30.0, context.getStack().peek());
    }

    @Test
    void testExecuteCommandsWithErrorThrows() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "ADD" // Недостаточно элементов в стеке
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands));
    }
}