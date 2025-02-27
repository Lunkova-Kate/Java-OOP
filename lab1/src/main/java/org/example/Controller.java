package org.example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Controller {
    private String inputFile;
    private String outputFile;

    public Controller(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void generateReport() {
        try (Reader reader = new FileReader(inputFile)) {
            ReportGenerator generator = new ReportGenerator();
            generator.analyze(reader);

            // Записываем результаты в выходной файл
            OutputFile outputFileWriter = new OutputFile(outputFile);
            outputFileWriter.writeReport(generator.getSortedWordStats());
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + inputFile);
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода при чтении файла: " + inputFile);
        }
    }
}