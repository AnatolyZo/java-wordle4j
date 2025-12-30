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
    private final Map<String, WordStatus> words = new LinkedHashMap<>();
    //Словарь возможных подсказок
    private final List<String> hintDictionary = new ArrayList<>();
    //Список букв, которые совпадают по значению и позиции в искомом слове
    private final Map<Integer, String> matchedPositionLetters = new HashMap<>();
    //Список букв, которые совпадают только по значению
    private final Map<String, List<Integer>> matchedLetters = new HashMap();
    //Список букв, которые отсутствуют в искомом слове
    private final List<String> dismatchedLetters = new ArrayList<>();
    private boolean isStepChanged = false;

    public void setWords(String word) {
        words.put(word, WordStatus.HINT_DOES_NOT_OFFERED);
    }

    public Map<String, WordStatus> getWords() {
        return words;
    }

    public List<String> getHintDictionary() {
        return hintDictionary;
    }

    public void setHintDictionary() {
        hintDictionary.clear();

        for (String word : getWords().keySet()) {
            if (getWords().get(word).equals(WordStatus.HINT_DOES_NOT_OFFERED)) {
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

    public Map<String, List<Integer>> getMatchedLetters() {
        return matchedLetters;
    }

    public void setMatchedLetters(String letter, Integer index) {
        List<Integer> indexesList;

        if (!matchedLetters.containsKey(letter)) {
            indexesList = new ArrayList<>();
        } else {
            indexesList = matchedLetters.get(letter);
        }

        indexesList.add(index);
        matchedLetters.put(letter, indexesList);
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
            for (Map.Entry<String, List<Integer>> entry : getMatchedLetters().entrySet()) {
                if (!word.contains(entry.getKey()) || entry.getValue().contains(word.indexOf(entry.getKey()))) {
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

    public String formatWord(String word) {
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'ё') {
                word = word.replace(word.charAt(i), 'е');
            }
        }

        return word;
    }
}
