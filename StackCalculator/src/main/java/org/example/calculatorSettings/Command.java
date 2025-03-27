package org.example.calculatorSettings;

public interface Command {
    void execute(ExecutionContext context, String[] args) throws CalculatorException;

}
