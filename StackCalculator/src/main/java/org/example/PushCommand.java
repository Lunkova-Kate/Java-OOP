package org.example;

public class PushCommand implements Command {
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        if (args.length < 1) {
            throw new CalculatorException("Push command doesn't have enough arguments");
        }

        String arg = args[0]; // PUSH x - тут аргумент x
        double value;

        // Если аргумент — это переменная, берем её значение из контекста
        if (context.getVariables().containsKey(arg)) {
            value = context.getVariables().get(arg);
        }
        // Иначе пытаемся преобразовать аргумент в число
        else {
            try {
                value = Double.parseDouble(arg); // PUSH 10 -> 10.0
            } catch (NumberFormatException e) {
                throw new CalculatorException("Invalid number or undefined variable: " + arg);
            }
        }

        context.getStack().push(value);
    }
}