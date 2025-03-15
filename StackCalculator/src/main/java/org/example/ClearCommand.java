package org.example;

import java.util.Stack;

public class ClearCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();
        stack.clear(); // Очищаем стек
        System.out.println("Stack cleared.");
    }
}