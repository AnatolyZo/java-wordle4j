package ru.yandex.practicum;

import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    /*Словарь в качестве ключа принимает слово, а в качестве значения будет использовано одно из 4 состояний
     этого с слова ("Введено пользователем", "Подсказка предлагалась", "Подсказка не предлагалась",
     "Подсказка исключена") для реализации возможностей по недопущению ввода уже повторных слов
     и для составления списка возможных подсказок*/
    private final Map<String, String> words;
    //Словарь возможных подсказок
    private final List<String> hintDictionary;
    //Список букв, которые совпадают по значению и позиции в искомом слове
    private final Map<Integer, String> matchedPositionLetters;
    //Список букв, которые совпадают только по значению
    private final List<String> matchedLetters;
    //Список букв, которые отсутствуют в искомом слове
    private final List<String> dismatchedLetters;
    private boolean isStepChanged;
    static final String WORD_INSERTED_BY_USER = "Введено пользователем";
    static final String HINT_OFFERED = "Подсказка предлагалась";
    static final String HINT_DOES_NOT_OFFERED = "Подсказка не предлагалась";
    static final String HINT_EXCLUDED = "Подсказка исключена";

    public WordleDictionary() {
        words = new LinkedHashMap<>();
        hintDictionary = new ArrayList<>();
        matchedPositionLetters = new HashMap<>();
        matchedLetters = new ArrayList<>();
        dismatchedLetters = new ArrayList<>();
        isStepChanged = false;
    }

    public void setWords(String word) {
        words.put(word, HINT_DOES_NOT_OFFERED);
    }

    public Map<String, String> getWords() {
        return words;
    }

    public List<String> getHintDictionary() {
        return hintDictionary;
    }

    public void setHintDictionary() {
        hintDictionary.clear();

        for (String word : getWords().keySet()) {
            if (getWords().get(word).equals(HINT_DOES_NOT_OFFERED)) {
                getHintDictionary().add(word);
            }
        }
    }

    public Map<Integer, String> getMatchedPositionLetters() {
        return matchedPositionLetters;
    }

    public void setMatchedPositionLetters(Integer index, String letter) {
        matchedPositionLetters.put(index, letter);
    }

    public List<String> getMatchedLetters() {
        return matchedLetters;
    }

    public void setMatchedLetters(String letter) {
        matchedLetters.add(letter);
    }

    public List<String> getDismatchedLetters() {
        return dismatchedLetters;
    }

    public void setDismatchedLetters(String letter) {
        dismatchedLetters.add(letter);
    }

    public boolean isStepChanged() {
        return isStepChanged;
    }

    public void setStepChanged(boolean stepChanged) {
        isStepChanged = stepChanged;
    }

    public boolean checkForDismatchedLetters(String word, boolean isWordMatch) {
        if (!getDismatchedLetters().isEmpty()) {
            for (int i = 0; i < getDismatchedLetters().size(); i++) {
                if (word.contains(getDismatchedLetters().get(i))) {
                    isWordMatch = false;
                    break;
                }
            }
        }

        return isWordMatch;
    }

    public boolean checkForMatchedLetters(String word, boolean isWordMatch) {
        if (isWordMatch & !getMatchedLetters().isEmpty()) {
            for (int i = 0; i < getMatchedLetters().size(); i++) {
                if (!word.contains(getMatchedLetters().get(i))) {
                    isWordMatch = false;
                    break;
                }
            }
        }

        return isWordMatch;
    }

    public boolean checkForMatchedPositionLetters(String word, boolean isWordMatch) {
        if (isWordMatch & !getMatchedPositionLetters().isEmpty()) {
            for (Integer index : getMatchedPositionLetters().keySet()) {
                if (!String.valueOf(word.charAt(index)).equals(getMatchedPositionLetters().get(index))) {
                    isWordMatch = false;
                    break;
                }
            }
        }

        return isWordMatch;
    }
}
