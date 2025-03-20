package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrintCommandTest {

    @Test
    void testPrint() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();
        PrintCommand printCommand = new PrintCommand();

        pushCommand.execute(context, new String[]{"10"});
        printCommand.execute(context, new String[]{});
        assertEquals(10.0, context.getStack().peek());
    }

    @Test
    void testPrintEmptyStack() {
        ExecutionContext context = new ExecutionContext();
        PrintCommand printCommand = new PrintCommand();

        assertThrows(CalculatorException.class, () -> printCommand.execute(context, new String[]{}));
    }
}