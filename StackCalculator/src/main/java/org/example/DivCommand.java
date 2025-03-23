package org.example;

import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DivCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DivCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            logger.error("Not enough elements in stack for DIV command");
            throw new CalculatorException("Not enough elements in stack for DIV command");
        }

        double a = stack.pop();
        double b = stack.pop();

        if (a == 0) {
            stack.push(a);
            stack.push(b);
            logger.error("Division by zero attempted");
            throw new CalculatorException("Division by zero");
        }

        stack.push(b / a);
        logger.info("Divided {} by {}, result: {}", b, a, b / a);
    }
}