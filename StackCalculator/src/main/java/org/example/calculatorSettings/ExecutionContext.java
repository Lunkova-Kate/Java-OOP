package org.example.calculatorSettings;
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

}
