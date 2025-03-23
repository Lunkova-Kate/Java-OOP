package org.example;

import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PopCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PopCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.isEmpty()) {
            logger.error("Stack is empty, nothing to pop");
            throw new CalculatorException("Stack is empty, nothing to pop");
        }

        double value = stack.pop();
        logger.info("Popped value: {}", value);
        System.out.println("Popped value: " + value);
    }
}