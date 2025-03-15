package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> commands = new ArrayList<>();

        // Режим файла
        if (args.length > 0) {
            String filePath = args[0];
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    commands.add(line.trim());
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                return;
            }
        }
        // Интерактивный режим
        else {
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
                        // Выполняем команду сразу

                        calculator.executeCommands(List.of(line));
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
                return;
            }
        }

        // Запуск калькулятора (для режима файла)
        if (!commands.isEmpty()) {
            Calculator calculator = new Calculator();
            calculator.executeCommands(commands);
        }
    }
}