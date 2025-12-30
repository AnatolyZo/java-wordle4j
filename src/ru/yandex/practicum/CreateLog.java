package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateLog {
    private static final Path path = Paths.get("log.txt");
    private static PrintWriter log;

    public static void createLogFile() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла.");
            }
        }
    }

    public static PrintWriter createPrintWriter() {
        try {
            log = new PrintWriter(new FileWriter("log.txt", true));
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }

        return log;
    }
}
