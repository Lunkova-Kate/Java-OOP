package org.example;
import java.util.Stack;

public class MulCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            throw new CalculatorException("Not enough elements in stack for MUL command");
        }

        double a = stack.pop();
        double b = stack.pop();
        stack.push(a * b);
    }
}
