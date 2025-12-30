package ru.yandex.practicum;

import ru.yandex.practicum.gamingexceptions.DuplicateException;
import ru.yandex.practicum.gamingexceptions.InvalidWordLengthException;
import ru.yandex.practicum.gamingexceptions.WordNotFoundInDictionary;

import java.io.PrintWriter;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    Random random = new Random();

    public WordleGame(WordleDictionary dictionary) {
        int dictionarySize = dictionary.getWords().size();
        dictionary.setHintDictionary();
        this.answer = dictionary.getHintDictionary().get(random.nextInt(dictionarySize));
        steps = 6;
        this.dictionary = dictionary;
    }

    public WordleGame(WordleDictionary dictionary, String answer) {
        this.answer = answer;
        steps = 6;
        this.dictionary = dictionary;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps() {
        steps--;
    }

    public WordleDictionary getDictionary() {
        return dictionary;
    }

    //Метод выдает результат сравнения введенного слова с искомым
    public String comparisonResult(String word, PrintWriter log) {
        word = formatInputedWord(word);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == answer.charAt(i)) {
                sb.append('+');
                dictionary.setMatchedPositionLetters(i, String.valueOf(word.charAt(i)));
            } else if (answer.contains(String.valueOf(word.charAt(i)))) {
                sb.append('^');
                dictionary.setMatchedLetters(String.valueOf(word.charAt(i)), i);
            } else {
                sb.append('-');
                dictionary.setDismatchedLetters(String.valueOf(word.charAt(i)));
            }
        }

        log.println(String.format("Пользователю выдан результат сравнения - %s, загаданное слово - %s", sb, word));
        log.flush();
        return sb.toString();
    }

    //Метод приводит слово к единому формату (строчные буквы, ё заменается на е)
    private String formatInputedWord(String word) {
        return dictionary.formatWord(word);
    }

    public boolean compareWord(String word) {
        return formatInputedWord(word).equals(answer);
    }

    //Метод помечает введенное слово для исключения повторного ввода
    public void addUsersWord(String usersWord) {
        dictionary.getWords().put(usersWord, WordStatus.WORD_INSERTED_BY_USER);
    }

    //Метод определяет соответствие введенного слова правилам игры
    public boolean processUsersWord(String usersWord) throws InvalidWordLengthException, WordNotFoundInDictionary, DuplicateException {
        if (usersWord.length() != 5) {
            throw new InvalidWordLengthException("Слово должно состоять ровно из 5 букв. Повторите ввод, попытка не засчитана.");
        } else if (!dictionary.getWords().containsKey(usersWord)) {
            throw new WordNotFoundInDictionary("Слово должно находиться в словаре. Повторите ввод, попытка не засчитана.");
        } else if (dictionary.getWords().get(usersWord).equals(WordStatus.WORD_INSERTED_BY_USER)) {
            throw new DuplicateException(String.format("Слово %s уже было введено ранее. Повторите ввод, попытка не засчитана.", usersWord));
        }

        return true;
    }

    //Метод по поиску подсказки
    public String showHint(PrintWriter log) {
        String hint;

        //Блок для обработки подсказки сразу на новом ходе
        if (dictionary.isStepChanged()) {
            identifyHints();
            dictionary.setStepChanged(false);
        }

        dictionary.setHintDictionary();
        hint = dictionary.getHintDictionary().get(random.nextInt(dictionary.getHintDictionary().size()));

        if (!hint.equals(answer)) {
            dictionary.getWords().put(hint, WordStatus.HINT_OFFERED);
            //Подсказка удаляется на случай, если пользователь решит повторно взять подсказку
            dictionary.getHintDictionary().remove(hint);
        }

        log.println(String.format("Пользователю дана подсказка - %s", hint));
        log.flush();

        return hint;
    }

    //Метод по отбору слов в качестве подсказок на основе уже введенных слов
    private void identifyHints() {
        boolean isWordMatch;

        for (String word : dictionary.getWords().keySet()) {
            isWordMatch = true;

            if (dictionary.getWords().get(word).equals(WordStatus.HINT_DOES_NOT_OFFERED)
                    || dictionary.getWords().get(word).equals(WordStatus.HINT_OFFERED)) {

                /*Последовательная проверка слова на наличие отсутствующих букв, на наличие присутствующих букв
                и на начличие букв, находящихся на верных позициях*/
                isWordMatch = dictionary.checkForDismatchedLetters(word, isWordMatch);
                isWordMatch = dictionary.checkForMatchedLetters(word, isWordMatch);
                isWordMatch = dictionary.checkForMatchedPositionLetters(word, isWordMatch);
            }

            if (!isWordMatch) {
                dictionary.getWords().put(word, WordStatus.HINT_EXCLUDED);
            }
        }
    }
}
