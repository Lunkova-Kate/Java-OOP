package org.example;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

public class ExecutionContext {
    private Stack<Double> stack = new Stack<>();
    private Map<String, Double> variables =  new HashMap<>();

    public Map<String, Double> getVariables() {
        return variables;
    }

    public Stack<Double> getStack() {
        return stack;
    }
    public void printStack() {
        System.out.println("Stack: " + stack);
    }
//    public double popStackWithCheck() throws CalculatorException {
//        if (stack.isEmpty()) {
//            throw new CalculatorException("Not enough elements in stack");
//        }
//        return stack.pop();
//    }
}
