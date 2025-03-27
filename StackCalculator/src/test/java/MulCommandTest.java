import org.example.calculatorSettings.Calculator;
import org.example.calculatorSettings.CalculatorException;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MulCommandTest {

    @Test
    void testMulCommandSuccessEquals() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "PUSH 20",
                "MUL"
        );
        calculator.executeCommands(commands);
        assertEquals(200.0, calculator.getContext().getStack().peek(), "Multiplication result should be 200.0");
    }

    @Test
    void testMulCommandNotEnoughElementsThrows() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "MUL"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "Not enough elements in stack for MUL command");
    }
}