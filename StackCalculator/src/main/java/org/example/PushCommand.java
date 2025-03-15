package org.example;

public class PushCommand implements Command{
    @Override
    public void execute(ExecutionContext context, String[] args) throws CalculatorException {
    if(args.length < 1) {
        throw new CalculatorException("Push command doesn't have enough arguments");
    }

    String arg = args[0]; // PUSH x - тут аргумент x
        double value;
        if(context.getVariables().containsKey(arg)){ //- проверяем мапу на наличие этого аргумента.
            value = context.getVariables().get(arg);
        }
        //иначе нам передали число ввиде строчки!!
        else{
            value = Double.parseDouble(arg); // PUSH 10 -> 10.0
        }
        context.getStack().push(value);
    }
}
