package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    static WordleDictionary dictionary;
    static WordleGame wordleGame;
    private static PrintWriter log = new PrintWriter(System.out);

    //Проверка метода, выдающего результат совпадения букв в введенном слове
    @Test
    public void comparisonResultMethodTest() {
        dictionary = new WordleDictionary();
        dictionary.setWords("набег");
        dictionary.setWords("тальк");
        dictionary.setWords("кабан");
        dictionary.setWords("астра");
        dictionary.setWords("надир");
        dictionary.setWords("дилен");
        dictionary.setWords("отсек");
        dictionary.setWords("жулан");
        dictionary.setWords("чибис");
        dictionary.setWords("чулан");
        wordleGame = new WordleGame(dictionary, "набег");

        String usersWord = "тальк";
        assertEquals("-+---", wordleGame.comparisonResult(usersWord, log));

        String usersWord2 = "кабан";
        assertEquals("-++^^", wordleGame.comparisonResult(usersWord2, log));
    }

    //Проверка метода, приводящего введенное слово к формату игры и сравнивающего его с загаданным словом
    @Test
    public void compareWordMethodTest() {
        dictionary = new WordleDictionary();
        dictionary.setWords("набег");
        dictionary.setWords("тальк");
        dictionary.setWords("кабан");
        dictionary.setWords("астра");
        dictionary.setWords("надир");
        dictionary.setWords("дилен");
        dictionary.setWords("отсек");
        dictionary.setWords("жулан");
        dictionary.setWords("чибис");
        dictionary.setWords("чулан");
        wordleGame = new WordleGame(dictionary, "набег");

        String usersWord = "НаБёг";
        assertTrue(wordleGame.compareWord(usersWord));

        String usersWord2 = "набег";
        assertTrue(wordleGame.compareWord(usersWord2));
    }

    //Проверка метода, формирующего список возможных подсказок и выдающего случайную подсказку
    @Test
    public void showHintMethodTest() {
        dictionary = new WordleDictionary();
        dictionary.setWords("набег");
        dictionary.setWords("тальк");
        dictionary.setWords("кабан");
        dictionary.setWords("астра");
        dictionary.setWords("надир");
        dictionary.setWords("дилен");
        dictionary.setWords("отсек");
        dictionary.setWords("жулан");
        dictionary.setWords("чибис");
        dictionary.setWords("чулан");
        wordleGame = new WordleGame(dictionary, "набег");

        List<String> expectedList = Arrays.asList("кабан", "набег", "надир");
        String usersWord = "жулан";
        wordleGame.addUsersWord(usersWord);
        wordleGame.comparisonResult(usersWord, log);
        wordleGame.getDictionary().setStepChanged(true);
        String hint = wordleGame.showHint(log);

        //Так как при работе метода предложенная подсказка исключается из списка подсказок,
        // то для корректного сравнения ее необходимо добавить
        if (!hint.equals("набег")) {
            wordleGame.getDictionary().getHintDictionary().add(hint);
        }

        List<String> actualList = wordleGame.getDictionary().getHintDictionary();

        assertEquals(expectedList.size(), actualList.size());
        assertTrue(expectedList.containsAll(actualList));
        assertTrue(actualList.containsAll(expectedList));

        /*В этом блоке происходит проверка метода после повторного запроса подсказки,
        так как в конкретном результате должно остаться только искомое слово,
        то блок if по добавлению подсказки не требуется*/
        List<String> expectedList2 = Arrays.asList("набег");
        String usersWord2 = "надир";
        wordleGame.addUsersWord(usersWord2);
        wordleGame.comparisonResult(usersWord2, log);
        wordleGame.getDictionary().setStepChanged(true);
        String hint2 = wordleGame.showHint(log);
        List<String> actualList2 = wordleGame.getDictionary().getHintDictionary();

        assertEquals(expectedList2.size(), actualList2.size());
        assertTrue(expectedList2.containsAll(actualList2));
        assertTrue(actualList2.containsAll(expectedList2));
    }

    @AfterEach
    public void returnDictionaryToOriginalState() {
        wordleGame.getDictionary().getHintDictionary().clear();

        for (String key : wordleGame.getDictionary().getWords().keySet()) {
            wordleGame.getDictionary().setWords(key);
        }

        wordleGame.getDictionary().getDismatchedLetters().clear();
        wordleGame.getDictionary().getMatchedLetters().clear();
        wordleGame.getDictionary().getMatchedPositionLetters().clear();
        wordleGame.getDictionary().setStepChanged(false);
    }
}
