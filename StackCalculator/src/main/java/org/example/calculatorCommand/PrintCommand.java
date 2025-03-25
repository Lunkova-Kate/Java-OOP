package org.example.calculatorCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.Command;
import org.example.calculatorSettings.ExecutionContext;

import java.util.Stack;

public class PrintCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PrintCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.isEmpty()) {
            logger.error("Stack is empty, nothing to print");
            throw new CalculatorException("Stack is empty, nothing to print");
        }

        double value = stack.peek();
        logger.info("Printed value: {}", value);
        System.out.println(value);
    }
}