package ru.yandex.practicum.nongamingexceptions;

public class DictionaryNotFoundException extends RuntimeException {
    public DictionaryNotFoundException(String message) {
        super(message);
    }
}
