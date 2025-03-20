package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SubCommandTest {

    @Test
    void testSubCommandSuccess() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 20",
                "PUSH 10",
                "SUB"
        );
        calculator.executeCommands(commands);
        assertEquals(10.0, calculator.getContext().getStack().peek(), "Subtraction result should be 10.0");
    }

    @Test
    void testSubCommandNotEnoughElements() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "SUB"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "Not enough elements in stack for SUB command");
    }
}