package org.example.calculatorCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.Command;
import org.example.calculatorSettings.ExecutionContext;

import java.util.Stack;

public class MulCommand implements Command {
    private static final Logger logger = LogManager.getLogger(MulCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            logger.error("Not enough elements in stack for MUL command");
            throw new CalculatorException("Not enough elements in stack for MUL command");
        }

        double a = stack.pop();
        double b = stack.pop();
        stack.push(a * b);
        logger.info("Multiplied {} and {}, result: {}", a, b, a * b);
    }
}