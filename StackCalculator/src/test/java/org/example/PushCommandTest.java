package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PushCommandTest {

    @Test
    void testPushNumber() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        pushCommand.execute(context, new String[]{"10"});
        assertEquals(10.0, context.getStack().peek());
    }

    @Test
    void testPushVariable() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        DefineCommand defineCommand = new DefineCommand();
        PushCommand pushCommand = new PushCommand();

        defineCommand.execute(context, new String[]{"A", "10"});
        pushCommand.execute(context, new String[]{"A"});
        assertEquals(10.0, context.getStack().peek());
    }

    @Test
    void testPushInvalidVariable() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        assertThrows(CalculatorException.class, () -> pushCommand.execute(context, new String[]{"B"}));
    }
    @Test
    void testPushInvalidNumber() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        // Попытка добавить нечисловое значение
        assertThrows(CalculatorException.class, () -> pushCommand.execute(context, new String[]{"abc"}));
    }

    @Test
    void testPushMissingArguments() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        // Попытка выполнить команду без аргументов
        assertThrows(CalculatorException.class, () -> pushCommand.execute(context, new String[]{}));
    }
}