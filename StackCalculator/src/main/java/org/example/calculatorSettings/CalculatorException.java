package org.example.calculatorSettings;

public class CalculatorException extends Exception{
    public CalculatorException(String message){
        super(message); //конструктор родителя C:
    }
    public CalculatorException(String message, Throwable cause){
        super(message,cause);
    }
}
