package org.example;
import java.util.Stack;

public class SubCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            throw new CalculatorException("Not enough elements in stack for SUB command");
        }

        double a = stack.pop();
        double b = stack.pop();
        stack.push(b - a);
    }
}
