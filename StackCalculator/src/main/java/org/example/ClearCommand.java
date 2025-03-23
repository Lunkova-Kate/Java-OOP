package org.example;

import java.util.Stack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClearCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ClearCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();
        stack.clear();
        logger.info("Stack cleared.");
        System.out.println("Stack cleared.");
    }
}