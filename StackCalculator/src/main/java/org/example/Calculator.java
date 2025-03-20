package org.example;

import java.util.List;

public class Calculator {
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
                command.execute(context, args);
            } catch (CalculatorException e) {
                System.err.println("Error executing command: " + commandLine);
                System.err.println("Reason: " + e.getMessage());
                throw e; // Пробрасываем исключение дальше
            }
        }
    }
}