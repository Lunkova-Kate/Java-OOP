package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testExecuteCommands() throws CalculatorException {
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
    void testExecuteCommandsWithError() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "ADD" 
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands));
    }
}