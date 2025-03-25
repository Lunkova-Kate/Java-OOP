package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.example.calculatorSettings.Calculator;
import org.example.calculatorSettings.CalculatorException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws CalculatorException {
        List<String> commands = new ArrayList<>();

        if (args.length > 0) {
            String filePath = args[0];
            try (Scanner fileScanner = new Scanner(new File(filePath))) {
                while (fileScanner.hasNextLine()) {
                    commands.add(fileScanner.nextLine().trim());
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
//продолжение
        if (!commands.isEmpty()) {
            logger.info("Entering file mode.");
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