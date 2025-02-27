package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class OutputFile {
    private String outputFile;

    public OutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public void writeReport(List<WordStat> stats) {
        File file = new File(outputFile);
        try (Writer writer = new FileWriter(file)) {
            writer.write("Слово;Частота;Частота (%)\n");
            // Вычисляем общее количество слов
            long totalWords = stats.stream().mapToLong(WordStat::getFrequency).sum();

            // Устанавливаем локаль US, чтобы десятичный разделитель был точкой :(
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("#.0", symbols); // Форматируем проценты до одного знака после запятой

            // Проходим по каждому слову и записываем его вместе с частотой и процентом
            for (WordStat stat : stats) {
                double percentage = (double) stat.getFrequency() / totalWords * 100;
                String row = stat.getWord() + ";" +
                        stat.getFrequency() + ";" +
                        df.format(percentage) + "%\n"; // Формируем строку для записи
                writer.write(row);
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + outputFile);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}