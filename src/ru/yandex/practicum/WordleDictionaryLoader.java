package ru.yandex.practicum;

import ru.yandex.practicum.nongamingexceptions.DictionaryIsEmptyException;
import ru.yandex.practicum.nongamingexceptions.DictionaryNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    public static WordleDictionary loadDictionary(PrintWriter log) throws DictionaryNotFoundException, DictionaryIsEmptyException {
        WordleDictionary wordleDictionary = new WordleDictionary();

        if (!Files.exists(Paths.get("words_ru.txt"))) {
            throw new DictionaryNotFoundException("Словарь не найден.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
            String word;

            while ((word = br.readLine()) != null) {
                if (word.length() == 5) {
                    wordleDictionary.setWords(wordleDictionary.formatWord(word));
                }
            }

            log.println("Пользователем успешно загружен словарь.");
            log.flush();
        } catch (IOException e) {
            log.println(e.getMessage());
            log.flush();
        }
        if (wordleDictionary.getWords().isEmpty()) {
            throw new DictionaryIsEmptyException("Словарь пуст.");
        }

        return wordleDictionary;
    }
}
