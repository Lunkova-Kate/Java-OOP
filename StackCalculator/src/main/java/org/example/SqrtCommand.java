package org.example;

import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SqrtCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SqrtCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.isEmpty()) {
            logger.error("Stack is empty, nothing to compute SQRT");
            throw new CalculatorException("Stack is empty, nothing to compute SQRT");
        }

        double a = stack.pop();

        if (a < 0) {
            logger.error("Cannot compute square root of a negative number");
            throw new CalculatorException("Cannot compute square root of a negative number");
        }

        double result = Math.sqrt(a);
        stack.push(result);
        logger.info("Computed square root of {}, result: {}", a, result);
    }
}