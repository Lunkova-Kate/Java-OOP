package org.example.calculatorSettings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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
                command.execute(context, args); //вызвали уже нужного класса!
            } catch (CalculatorException e) {
                logger.error("Error executing command: {}", commandLine, e);
                throw e;
            }
        }
    }
}