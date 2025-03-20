package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DivCommandTest {

    @Test
    void testDivCommandSuccess() throws CalculatorException {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 20",
                "PUSH 10",
                "DIV"
        );
        calculator.executeCommands(commands);
        assertEquals(2.0, calculator.getContext().getStack().peek(), "Division result should be 2.0");
    }

    @Test
    void testDivCommandDivisionByZero() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 20",
                "PUSH 0",
                "DIV"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "Division by zero should throw an exception");
    }

    @Test
    void testDivCommandNotEnoughElements() {
        Calculator calculator = new Calculator();
        List<String> commands = List.of(
                "PUSH 10",
                "DIV"
        );
        assertThrows(CalculatorException.class, () -> calculator.executeCommands(commands), "Not enough elements in stack for DIV command");
    }
}