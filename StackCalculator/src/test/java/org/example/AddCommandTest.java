package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AddCommandTest {

    @Test
    void testAdd() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();
        AddCommand addCommand = new AddCommand();

        pushCommand.execute(context, new String[]{"10"});
        pushCommand.execute(context, new String[]{"20"});
        addCommand.execute(context, new String[]{});
        assertEquals(30.0, context.getStack().peek());
    }

    @Test
    void test1AddNotEnoughElements() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();
        AddCommand addCommand = new AddCommand();

        pushCommand.execute(context, new String[]{"10"});
        assertThrows(CalculatorException.class, () -> addCommand.execute(context, new String[]{}));
    }



}