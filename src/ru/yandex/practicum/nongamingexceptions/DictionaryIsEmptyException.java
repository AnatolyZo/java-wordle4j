package ru.yandex.practicum.nongamingexceptions;

public class DictionaryIsEmptyException extends RuntimeException {
    public DictionaryIsEmptyException(String message) {
        super(message);
    }
}
