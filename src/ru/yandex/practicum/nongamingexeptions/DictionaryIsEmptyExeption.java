package ru.yandex.practicum.nongamingexeptions;

public class DictionaryIsEmptyExeption extends RuntimeException {
    public DictionaryIsEmptyExeption(String message) {
        super(message);
    }
}
