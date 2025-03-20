package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PopCommandTest {

    @Test
    void testPopCommandSuccess() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "POP"
        );
        calculator.executeCommands(commands);
        assertTrue(calculator.getContext().getStack().isEmpty(), "Stack should be empty after POP command");
    }

    @Test
    void testPopCommandEmptyStack() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "POP"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "POP on empty stack should throw an exception");
    }
}