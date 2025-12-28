package ru.yandex.practicum.nongamingexeptions;

public class DictionaryNotFoundExeption extends RuntimeException {
    public DictionaryNotFoundExeption(String message) {
        super(message);
    }
}
