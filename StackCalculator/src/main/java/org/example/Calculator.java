package org.example;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Calculator {
    private static final Logger logger = LogManager.getLogger(Calculator.class);
    private ExecutionContext context = new ExecutionContext();
    private CommandFactory commandFactory = new CommandFactory();

    public ExecutionContext getContext() {
        return context;
    }

    public void executeCommands(List<String> commands) throws CalculatorException {
        for (String commandLine : commands) {
            String[] parts = commandLine.split(" ");
            String commandName = parts[0];
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);
            try {
                Command command = commandFactory.getCommand(commandName);
                logger.info("Executing command: {}", commandLine);
                command.execute(context, args);
            } catch (CalculatorException e) {
                logger.error("Error executing command: {}", commandLine, e);
                throw e;
            }
        }
    }
}