package ru.yandex.practicum;

import ru.yandex.practicum.nongamingexeptions.DictionaryIsEmptyExeption;
import ru.yandex.practicum.nongamingexeptions.DictionaryNotFoundExeption;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    public static WordleDictionary loadDictionary() throws DictionaryNotFoundExeption, DictionaryIsEmptyExeption {
        WordleDictionary wordleDictionary = new WordleDictionary();

        if (!Files.exists(Paths.get("words_ru.txt"))) {
            throw new DictionaryNotFoundExeption("Словарь не найден.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
            while (br.ready()) {
                String word = br.readLine();
                if (word.length() == 5) {
                    word = word.toLowerCase();
                    for (int i = 0; i < word.length(); i++) {
                        if (word.charAt(i) == 'ё') {
                            word = word.replace(word.charAt(i), 'е');
                        }
                    }
                    wordleDictionary.setWords(word);
                }
            }

            PrintWriter.writeLog("Пользователем успешно загружен словарь.");
        } catch (IOException e) {
            PrintWriter.writeLog(e.getMessage());
        }
        if (wordleDictionary.getWords().isEmpty()) {
            throw new DictionaryIsEmptyExeption("Словарь пуст.");
        }

        return wordleDictionary;
    }
}
