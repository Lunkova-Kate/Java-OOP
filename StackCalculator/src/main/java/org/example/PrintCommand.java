package org.example;
import java.util.Stack;

public class PrintCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.isEmpty()) {
            throw new CalculatorException("Stack is empty, nothing to print");
        }

        System.out.println(stack.peek());
    }
}
