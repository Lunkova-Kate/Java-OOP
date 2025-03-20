package org.example;

public class DefineCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        if (args.length < 2) { // DEFINE x 10
            throw new CalculatorException("DEFINE command doesn't have enough arguments");
        }

        String variableName = args[0];
        double value;

        try {
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Invalid number: " + args[1]);
        }

        context.getVariables().put(variableName, value); // В мапу положили переменную
    }
}