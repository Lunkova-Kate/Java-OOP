package org.example;

public interface Command {
    void execute(ExecutionContext context,String[] args) throws CalculatorException;

}
