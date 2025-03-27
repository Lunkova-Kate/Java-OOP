import org.example.calculatorCommand.AddCommand;
import org.example.calculatorCommand.PushCommand;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.ExecutionContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddCommandTest {

    @Test
    void testAddAssertEquals() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();
        AddCommand addCommand = new AddCommand();

        pushCommand.execute(context, new String[]{"10"});
        pushCommand.execute(context, new String[]{"20"});
        addCommand.execute(context, new String[]{});
        assertEquals(30.0, context.getStack().peek());
    }

    @Test
    void testAddNotEnoughElementsThrows() throws CalculatorException {
        ExecutionContext context = new ExecutionContext();
        PushCommand pushCommand = new PushCommand();
        AddCommand addCommand = new AddCommand();

        pushCommand.execute(context, new String[]{"10"});
        assertThrows(CalculatorException.class, () -> addCommand.execute(context, new String[]{}));
    }



}