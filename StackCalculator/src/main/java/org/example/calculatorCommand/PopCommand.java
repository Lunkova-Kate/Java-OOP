package org.example.calculatorCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.Command;
import org.example.calculatorSettings.ExecutionContext;

import java.util.Stack;

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