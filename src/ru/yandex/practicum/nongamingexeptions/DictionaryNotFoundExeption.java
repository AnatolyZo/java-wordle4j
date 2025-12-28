package ru.yandex.practicum;

public class DictionaryNotFoundExeption extends Exception {
    public DictionaryNotFoundExeption(String message) {
        super(message);
    }
}
