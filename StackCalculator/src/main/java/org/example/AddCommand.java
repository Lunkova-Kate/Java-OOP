package org.example;

import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AddCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            logger.error("Not enough elements in stack for ADD command");
            throw new CalculatorException("Not enough elements in stack for ADD command");
        }

        double a = stack.pop();
        double b = stack.pop();
        stack.push(a + b);

        logger.info("Added {} and {}, result: {}", a, b, a + b);

    }
}