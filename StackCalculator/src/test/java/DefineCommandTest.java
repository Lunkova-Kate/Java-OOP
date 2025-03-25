import org.example.calculatorCommand.DefineCommand;
import org.example.calculatorCommand.PushCommand;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefineCommandTest {

    @Test
    void testDefineVariableEquels() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        DefineCommand defineCommand = new DefineCommand();

        defineCommand.execute(context, new String[]{"A", "10"});
        assertEquals(10.0, context.getVariables().get("A"));
    }

    @Test
    void testDefineInvalidNumberThrows() {
        ExecutionContext context = new ExecutionContext();
        DefineCommand defineCommand = new DefineCommand();

        assertThrows(CalculatorException.class, () -> defineCommand.execute(context, new String[]{"A", "abc"}));
    }

    @Test
    void testDefineMissingArgumentsThrows() {
        ExecutionContext context = new ExecutionContext();
        DefineCommand defineCommand = new DefineCommand();

        assertThrows(CalculatorException.class, () -> defineCommand.execute(context, new String[]{"A"}));
    }
    @Test
    void testPushInvalidNumberThrows() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        // Попытка добавить нечисловое значение
        assertThrows(CalculatorException.class, () -> pushCommand.execute(context, new String[]{"abc"}));
    }

    @Test
    void testPushMissingArgumentsThrows() {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();

        // Попытка выполнить команду без аргументов
        assertThrows(CalculatorException.class, () -> pushCommand.execute(context, new String[]{}));
    }
}