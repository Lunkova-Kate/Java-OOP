package org.example;

import java.util.Stack;

public class PopCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        Stack<Double> stack = context.getStack();

        if (stack.isEmpty()) {
            throw new CalculatorException("Stack is empty, nothing to pop");
        }

        double value = stack.pop(); // Удаляем верхний элемент из стека
        System.out.println("Popped value: " + value); // Выводим извлеченное значение
    }
}