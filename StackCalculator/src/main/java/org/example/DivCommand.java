package org.example;
import java.util.Stack;


public class DivCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.size() < 2) {
            throw new CalculatorException("Not enough elements in stack for DIV command");
        }

        double a = stack.pop();
        double b = stack.pop();

        if (a == 0) {
            throw new CalculatorException("Division by zero");
        }

        stack.push(b / a);
    }
}