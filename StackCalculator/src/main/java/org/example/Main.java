package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws CalculatorException {
        List<String> commands = new ArrayList<>();

        if (args.length > 0) {
            String filePath = args[0];
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    commands.add(line.trim());
                }
                logger.info("Loaded commands from file: {}", filePath);
            } catch (IOException e) {
                logger.error("Error reading file: {}", filePath, e);
                return;
            }
        } else {
            logger.info("Entering interactive mode.");
            System.out.println("Entering interactive mode. Type 'EXIT' to quit.");
            Calculator calculator = new Calculator();
            try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while (true) {
                    System.out.print("> ");
                    line = consoleReader.readLine().trim();

                    if (line.equalsIgnoreCase("EXIT")) {
                        break;
                    }
                    try {
                        calculator.executeCommands(List.of(line));
                    } catch (Exception e) {
                        logger.error("Error executing command: {}", line, e);
                    }
                }
            } catch (IOException e) {
                logger.error("Error reading input", e);
                throw new RuntimeException(e);
            }
        }

        if (!commands.isEmpty()) {
            Calculator calculator = new Calculator();
            try {
                calculator.executeCommands(commands);
            } catch (CalculatorException e) {
                logger.error("Error executing commands", e);
                throw new RuntimeException(e);
            }
        }
    }
}