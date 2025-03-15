package org.example;

import java.util.List;

public class Calculator {
    private ExecutionContext context = new ExecutionContext();
    private CommandFactory commandFactory = new CommandFactory();

    public void executeCommands(List<String> commands) {
        for (String commandLine : commands) {
            String[] parts = commandLine.split(" "); // Делим по пробелам
            String commandName = parts[0];
            String[] args = new String[parts.length - 1]; // Остались только аргументы
            System.arraycopy(parts, 1, args, 0, args.length); // Копируем в args

            try {
                Command command = commandFactory.getCommand(commandName);
                command.execute(context, args);
                context.printStack(); // Отладочный вывод
            } catch (CalculatorException e) {
                System.err.println("Error executing command: " + commandLine);
                System.err.println("Reason: " + e.getMessage());
            }
        }
    }
}