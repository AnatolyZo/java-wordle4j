package ru.yandex.practicum;

import ru.yandex.practicum.gamingexceptions.AttemptsEndedException;
import ru.yandex.practicum.gamingexceptions.DuplicateException;
import ru.yandex.practicum.gamingexceptions.InvalidWordLengthException;
import ru.yandex.practicum.gamingexceptions.WordNotFoundInDictionary;

import java.io.PrintWriter;
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
        try (PrintWriter log = CreateLog.createPrintWriter()) {
            CreateLog.createLogFile();

            WordleGame wordleGame = new WordleGame(WordleDictionaryLoader.loadDictionary(log));

            log.println("Пользователем успешно создана новая игра.");
            log.flush();
            System.out.println("Загадано случайное слово, попытайтесь его отгадать.");

            while (wordleGame.getSteps() != 0) {

                while (!isWordComplianceRules) {
                    System.out.println("Введите ваш вариант ответа:");
                    usersWord = scanner.nextLine();

                    if (usersWord.isEmpty()) {
                        System.out.println("Подсказка - " + wordleGame.showHint(log));
                    } else {
                        log.println(String.format("Пользователем введено слово - %s.", usersWord));
                        log.flush();
                        try {
                            isWordComplianceRules = wordleGame.processUsersWord(usersWord);
                        } catch (InvalidWordLengthException | WordNotFoundInDictionary | DuplicateException e) {
                            System.out.println(e.getMessage());
                            log.println(e.getMessage());
                            log.flush();
                        }
                    }
                }

                if (wordleGame.compareWord(usersWord)) {
                    System.out.printf("Совершенно верно! Загаданное слово - %s.", wordleGame.getAnswer());
                    return;
                } else {
                    wordleGame.addUsersWord(usersWord);
                    System.out.println(wordleGame.comparisonResult(usersWord, log));
                    wordleGame.setSteps();
                    wordleGame.getDictionary().setStepChanged(true);
                    System.out.printf("Ответ неверный, попробуйте еще раз. Оставшееся количество попыток - %d.%n", wordleGame.getSteps());
                    isWordComplianceRules = false;
                }
            }

            throw new AttemptsEndedException("Попытки закончились. Игра завершена.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
