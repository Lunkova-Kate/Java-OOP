package org.example;
import java.util.Stack;

public class SqrtCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.isEmpty()) {
            throw new CalculatorException("Stack is empty, nothing to compute SQRT");
        }

        double a = stack.pop();

        if (a < 0) {
            throw new CalculatorException("Cannot compute square root of a negative number");
        }

        stack.push(Math.sqrt(a)); //import java.lang.Math; с:
    }
}