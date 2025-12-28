package ru.yandex.practicum;

import ru.yandex.practicum.gamingexeptions.AttemptsEndedExeption;
import ru.yandex.practicum.gamingexeptions.DuplicateExeption;
import ru.yandex.practicum.gamingexeptions.InvalidWordLengthException;
import ru.yandex.practicum.gamingexeptions.WordNotFoundInDictionary;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isWordComplianceRules = false;
    private static String usersWord;

    public static void main(String[] args) {
        try {
            WordleGame wordleGame = new WordleGame(WordleDictionaryLoader.loadDictionary());
            PrintWriter.writeLog("Пользователем успешно создана новая игра.");
            System.out.println("Загадано случайное слово, попытайтесь его отгадать.");

            while (wordleGame.getSteps() != 0) {

                while (!isWordComplianceRules) {
                    System.out.println("Введите ваш вариант ответа:");
                    usersWord = scanner.nextLine();

                    if (usersWord.isEmpty()) {
                        System.out.println("Подсказка - " + wordleGame.showHint());
                    } else {
                        PrintWriter.writeLog(String.format("Пользователем введено слово - %s.", usersWord));
                        try {
                            isWordComplianceRules = wordleGame.processUsersWord(usersWord);
                        } catch (InvalidWordLengthException | WordNotFoundInDictionary | DuplicateExeption e) {
                            System.out.println(e.getMessage());
                            PrintWriter.writeLog(e.getMessage());
                        }
                    }
                }

                if (wordleGame.compareWord(usersWord)) {
                    System.out.printf("Совершенно верно! Загаданное слово - %s.", wordleGame.getAnswer());
                    return;
                } else {
                    wordleGame.addUsersWord(usersWord);
                    System.out.println(wordleGame.comparisonResult(usersWord));
                    wordleGame.setSteps();
                    wordleGame.getDictionary().setStepChanged(true);
                    System.out.printf("Ответ неверный, попробуйте еще раз. Оставшееся количество попыток - %d.%n", wordleGame.getSteps());
                    isWordComplianceRules = false;
                }
            }

            throw new AttemptsEndedExeption("Попытки закончились. Игра завершена.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            PrintWriter.writeLog(e.getMessage());
        }
    }
}
