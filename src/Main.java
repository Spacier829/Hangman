import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Main {
    // Обозначения переменных глобальных
    private static Random random = new Random();
    private static String MYSTERIOUS_WORD;
    private static String MASKED_WORD;

    private static int WORDS_COUNT = 51297;

    public static void main(String[] args) {
//        запуск основного цикла игры
        MASKED_WORD = "_";
        createMaskedWord(selectRandomWordFromDictionary());
        MYSTERIOUS_WORD = "барабан";
        MASKED_WORD = "б_____н";
        ArrayList<Integer> b = getIndexesOfEqualLetters("а");
        printOpenLetters(b);
        int a = 123;
    }

    // меню игры (начать, выйти из игры)
    public static void gameMenu() {

    }

    // основной цикл игры
    public static void gameLoop() {
    }

    // Выбор случайного слова из словаря
    public static String selectRandomWordFromDictionary() {
        try (Scanner fileScanner = new Scanner(new File("./Dictionary/dictionary.txt"))) {
            if (fileScanner.hasNextLine()) {
                int wordLineID = random.nextInt(WORDS_COUNT);
                for (int i = 0; i < wordLineID; i++) {
                    fileScanner.nextLine();
                }
                MYSTERIOUS_WORD = fileScanner.nextLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return MYSTERIOUS_WORD;
    }


    // выбор 2 случаных букв из слова
    public static void createMaskedWord(String mysteriousWord) {
        StringBuilder maskedWord = new StringBuilder(mysteriousWord);
        int numberOfLettersOfWord = mysteriousWord.length();
        int openLetterID = random.nextInt(numberOfLettersOfWord);

        for (int maskedLetterID = 0; maskedLetterID < numberOfLettersOfWord; maskedLetterID++) {
            {
                maskedWord.setCharAt(maskedLetterID, '_');
            }
        }

        ArrayList<Integer> indexes = getIndexesOfEqualLetters(String.valueOf(MYSTERIOUS_WORD.charAt(openLetterID)));
//        new ArrayList<Integer>([firstOpenLetterID, secondOpenLetterID]);
//
        MASKED_WORD = maskedWord.toString();

        printOpenLetters(indexes);

        int a = 1234;
//        return MASKED_WORD = maskedWord.toString();
    }

    // Пользовательский ввод буквы
    // (проверка на дебила и т.д. пока не знаю)
    public static void inputChar() {

    }

    // Обработка пользовательского ввода
    public static void identifierUserInput() {
        //if () true -> openmasked();
//        false -> error_count++ -> printGallow;
    }

    // Проверка наличия буквы в слове
    public static boolean checkCharFromWord(String letter) {
        if (MYSTERIOUS_WORD.contains(letter)) return true;
        return false;
    }

    // Получение индексов угаданных букв
    public static ArrayList<Integer> getIndexesOfEqualLetters(String letter) {
        ArrayList<Integer> lettersIndexes = new ArrayList<Integer>();
        int index = 0;
        while (index != -1) {
            index = MYSTERIOUS_WORD.indexOf(letter, index + 1);
            if (index != -1) {
                lettersIndexes.add(index);
            }
        }
        return lettersIndexes;
    }

    // Открытие угаданных букв
    public static void printOpenLetters(ArrayList<Integer> letterIndexes) {
        StringBuilder maskedWord = new StringBuilder(MASKED_WORD);
        for (int index : letterIndexes) {
            if (MASKED_WORD.charAt(index) != MYSTERIOUS_WORD.charAt(index)) {
                maskedWord.setCharAt(index, MYSTERIOUS_WORD.charAt(index));
            }
            MASKED_WORD = maskedWord.toString();
        }
        // получаем индекс
        // смотрим, что по этому индексу в оригинальном слове
        // смотрим, что по этому индексу в замаскированном слове
        // если такая буква еще не открыта -> открываем
        // если такая буква уже открыта -> говорим, что такое уже есть
    }

    // проверка что указанная буква уже была открыта
    public static boolean checkLetterIsAlreadyOpen() {
        return false;
    }

    // отрисовка слова
    public static void printmysteriousWord() {

    }

    // отрисовка виселицы
    public static void printGallows() {

    }


}
