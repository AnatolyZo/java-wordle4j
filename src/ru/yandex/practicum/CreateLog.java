package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogWriter {
    private static PrintWriter log;
    private static final Path path = Paths.get("log2.txt");

    public static void createLog() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла.");
            }
        }
    }

    public static void writeLog() {
        try {
            log = new PrintWriter(new FileWriter("log2.txt", true));
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }

    }
//    static Path path = Paths.get("log.txt");
//
//    public PrintWriter() {
//    }
//
//    public static void writeLog(String list) {
//        if (!Files.exists(path)) {
//            try {
//                Files.createFile(path);
//            } catch (IOException e) {
//                PrintWriter.writeLog("Ошибка при создании файла.");
//            }
//        }
//
//        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
//                fileWriter.write((list + "\n"));
//        } catch (IOException exp) {
//            PrintWriter.writeLog("Произошла ошибка во время записи файла.");
//        }
//    }
}
