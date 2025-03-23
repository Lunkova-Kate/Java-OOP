package org.example;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class CommandFactory {
    private Map<String, Command> commands = new HashMap<>();

    public CommandFactory() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("command.properties")) {
            if (input == null) {
                throw new RuntimeException("Failed to load commands.properties: file not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load commands.properties", e);
        }

        for (String commandName : properties.stringPropertyNames()) {
            String className = properties.getProperty(commandName);

            try {
                Class<?> clazz = Class.forName(className);
                Constructor<?> constructor = clazz.getConstructor();
                Command command = (Command) constructor.newInstance();
                commands.put(commandName.toUpperCase(), command);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create command: " + commandName, e);
            }
        }
    }

    public Command getCommand(String commandName) throws CalculatorException {
        Command command = commands.get(commandName.toUpperCase());

        if (command == null) {
            throw new CalculatorException("Unknown command: " + commandName);
        }

        return command;
    }
}
