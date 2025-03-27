package org.example.calculatorCommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.calculatorSettings.CalculatorException;
import org.example.calculatorSettings.Command;
import org.example.calculatorSettings.ExecutionContext;

public class DefineCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DefineCommand.class);

    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
        if (args.length < 2) {
            logger.error("DEFINE command doesn't have enough arguments");
            throw new CalculatorException("DEFINE command doesn't have enough arguments");
        }

        String variableName = args[0];
        double value;

        try {
            value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            logger.error("Invalid number: {}", args[1]);
            throw new CalculatorException("Invalid number: " + args[1]);
        }

        context.getVariables().put(variableName, value);
        logger.info("Defined variable {} with value {}", variableName, value);
    }
}