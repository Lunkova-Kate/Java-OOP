package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void testGetCommand() throws CalculatorException {
        CommandFactory commandFactory = new CommandFactory();

        Command pushCommand = commandFactory.getCommand("PUSH");
        assertTrue(pushCommand instanceof PushCommand);

        Command defineCommand = commandFactory.getCommand("DEFINE");
        assertTrue(defineCommand instanceof DefineCommand);

        Command addCommand = commandFactory.getCommand("ADD");
        assertTrue(addCommand instanceof AddCommand);

        Command subCommand = commandFactory.getCommand("SUB");
        assertTrue(subCommand instanceof SubCommand);

        Command mulCommand = commandFactory.getCommand("MUL");
        assertTrue(mulCommand instanceof MulCommand);

        Command divCommand = commandFactory.getCommand("DIV");
        assertTrue(divCommand instanceof DivCommand);

        Command sqrtCommand = commandFactory.getCommand("SQRT");
        assertTrue(sqrtCommand instanceof SqrtCommand);

        Command printCommand = commandFactory.getCommand("PRINT");
        assertTrue(printCommand instanceof PrintCommand);

        Command clearCommand = commandFactory.getCommand("CLEAR");
        assertTrue(clearCommand instanceof ClearCommand);

        Command popCommand = commandFactory.getCommand("POP");
        assertTrue(popCommand instanceof PopCommand);
    }

    @Test
    void testGetInvalidCommand() {
        CommandFactory commandFactory = new CommandFactory();

        assertThrows(CalculatorException.class, () -> commandFactory.getCommand("INVALID"));
    }
}