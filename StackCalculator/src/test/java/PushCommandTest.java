import org.example.calculatorCommand.DefineCommand;
import org.example.calculatorCommand.PushCommand;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushCommandTest {

    @Test
    void testPushNumberEquals() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        pushCommand.execute(context, new String[]{"10"});
        assertEquals(10.0, context.getStack().peek());
    }

    @Test
    void testPushVariableEquals() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        DefineCommand defineCommand = new DefineCommand();
        PushCommand pushCommand = new PushCommand();

        defineCommand.execute(context, new String[]{"A", "10"});
        pushCommand.execute(context, new String[]{"A"});
        assertEquals(10.0, context.getStack().peek());
    }

    @Test
    void testPushInvalidVariableThrows() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        assertThrows(CalculatorException.class, () -> pushCommand.execute(context, new String[]{"B"}));
    }
    @Test
    void testPushInvalidNumberThrows() {
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