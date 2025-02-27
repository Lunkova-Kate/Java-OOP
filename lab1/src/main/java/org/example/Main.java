package org.example;



public class Main {
    public static void main(String[] args) {
        try {
            StreamParser parser = new StreamParser(args);
            Controller controller = new Controller(parser.getInputFile(), parser.getOutputFile());
            controller.generateReport();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}