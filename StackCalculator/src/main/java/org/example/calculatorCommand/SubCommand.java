package org.example.calculatorCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.Command;
import org.example.calculatorSettings.ExecutionContext;

import java.util.Stack;

public class SubCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SubCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            logger.error("Not enough elements in stack for SUB command");
            throw new CalculatorException("Not enough elements in stack for SUB command");
        }

        double a = stack.pop();
        double b = stack.pop();
        stack.push(b - a);
        logger.info("Subtracted {} from {}, result: {}", a, b, b - a);
    }
}