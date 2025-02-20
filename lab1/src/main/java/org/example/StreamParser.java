package org.example;

public class StreamParser {
    private String inputFile;
    private String outputFile;

    public StreamParser(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Использование: ./program <inputFile> <outputFile>");
        }
        this.inputFile = args[0];
        this.outputFile = args[1];
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }
}