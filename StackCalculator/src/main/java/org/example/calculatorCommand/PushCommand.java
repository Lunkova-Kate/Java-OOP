package org.example.calculatorCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.Command;
import org.example.calculatorSettings.ExecutionContext;

public class PushCommand implements Command {
    private static final Logger logger = LogManager.getLogger(PushCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        if (args.length < 1) {
            logger.error("Push command doesn't have enough arguments");
            throw new CalculatorException("Push command doesn't have enough arguments");
        }

        String arg = args[0];
        double value;

        if (context.getVariables().containsKey(arg)) {
            value = context.getVariables().get(arg);
            logger.info("Pushed variable {} with value {}", arg, value);
        } else {
            try {
                value = Double.parseDouble(arg);
                logger.info("Pushed value: {}", value);
            } catch (NumberFormatException e) {
                logger.error("Invalid number or undefined variable: {}", arg);
                throw new CalculatorException("Invalid number or undefined variable: " + arg);
            }
        }

        context.getStack().push(value);
    }
}