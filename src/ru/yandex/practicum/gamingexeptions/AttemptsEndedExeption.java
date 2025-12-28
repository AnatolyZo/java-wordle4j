package ru.yandex.practicum;

public class AttemptsEndedExeption extends Exception {
    public AttemptsEndedExeption(String message) {
        super(message);
    }
}
