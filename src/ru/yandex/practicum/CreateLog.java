package ru.yandex.practicum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateLog {
    private static final Path path = Paths.get("log.txt");

    public static void createLog() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла.");
            }
        }
    }
}
