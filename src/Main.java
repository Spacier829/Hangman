import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Main {
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);

    // Загаданное слово
    private static String MYSTERIOUS_WORD;

    // Замаскированное слово
    private static String MASKED_WORD;

    // Количество слов в словаре
    private static int WORDS_COUNT = 51297;

    // Количество ошибок
    private static int ERROR_COUNT = 0;

    // Максимальное число допустимых ошибок
    private static int ERROR_MAX_COUNT = 6;

    // Список для хранения неверно введенных букв
    private static ArrayList<String> WRONG_LETTERS = new ArrayList<>();

    public static void main(String[] args) {
        startGameOrExit();
    }

    // Метод выбора начала игры или выхода
    public static void startGameOrExit() {
        String userChoice;
        do {
            System.out.println("Введите 'старт' чтобы начать игру или 'выход', чтобы выйти");
            userChoice = scanner.nextLine();
            if (userChoice.equals("старт")) {
                gameLoop();
            } else {
                System.out.println("Некорректный ввод, попробуйте еще раз.");
            }
        } while (!userChoice.equals("выход"));
    }

    // Основной цикл игры
    public static void gameLoop() {
        MYSTERIOUS_WORD = selectRandomWordFromDictionary();
        createMaskedWord(MYSTERIOUS_WORD);
        printGallows(ERROR_COUNT);
        do {
            System.out.println(MYSTERIOUS_WORD);// для отладки
            identifyUserInput(userInputLetter());
        } while (ERROR_COUNT != ERROR_MAX_COUNT && !MASKED_WORD.equals(MYSTERIOUS_WORD));
        checkGameState();
        clearInfo();
    }

    // Метод проверки победы или поражения
    public static void checkGameState() {
        if (MASKED_WORD.equals(MYSTERIOUS_WORD)) {
            System.out.println("Поздравляем! Вы победили!");
        } else {
            System.out.println("К сожалению Вы проиграли.");
            System.out.println("Загаданное слово: " + MYSTERIOUS_WORD);
        }
    }

    // Метод выбора случайного слова из словаря
    public static String selectRandomWordFromDictionary() {
        try (Scanner fileScanner = new Scanner(new File("./Dictionary/dictionary.txt"))) {
            if (fileScanner.hasNextLine()) {
                int wordLineID = random.nextInt(WORDS_COUNT);
                for (int i = 0; i < wordLineID; i++) {
                    fileScanner.nextLine();
                }
                MYSTERIOUS_WORD = fileScanner.nextLine().toLowerCase();
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return MYSTERIOUS_WORD;
    }

    // Метод создания замаскированного слова с 1 открытой буквой
    public static void createMaskedWord(String mysteriousWord) {
        StringBuilder maskedWord = new StringBuilder(mysteriousWord);
        int numberOfLettersOfWord = mysteriousWord.length();

        for (int maskedLetterID = 0; maskedLetterID < numberOfLettersOfWord; maskedLetterID++) {
            {
                maskedWord.setCharAt(maskedLetterID, '_');
            }
        }

        // Выбирается случайным образом индекс буквы, после чего эта буква открывается в замаскированном слове
        int helpLetterID = random.nextInt(numberOfLettersOfWord);
        ArrayList<Integer> indexesOfHelpLetters;
        indexesOfHelpLetters = getIndexesOfEqualLetters(String.valueOf(MYSTERIOUS_WORD.charAt(helpLetterID)));
        MASKED_WORD = maskedWord.toString().toLowerCase();
        openLettersInMaskedWord(indexesOfHelpLetters);
    }

    // Метод возвращения индексов угаданных букв
    public static ArrayList<Integer> getIndexesOfEqualLetters(String letter) {
        ArrayList<Integer> lettersIndexes = new ArrayList<>();
        int index = -1;
        do {
            index = MYSTERIOUS_WORD.indexOf(letter, index + 1);
            if (index != -1) {
                lettersIndexes.add(index);
            }
        } while (index != -1);
        return lettersIndexes;
    }

    // Метод открытия угаданных букв
    public static void openLettersInMaskedWord(ArrayList<Integer> letterIndexes) {
        StringBuilder maskedWord = new StringBuilder(MASKED_WORD);
        for (int index : letterIndexes) {
            if (MASKED_WORD.charAt(index) != MYSTERIOUS_WORD.charAt(index)) {
                maskedWord.setCharAt(index, MYSTERIOUS_WORD.charAt(index));
            }
            MASKED_WORD = maskedWord.toString();
        }
    }

    // Пользовательский ввод буквы
    public static String userInputLetter() {
        System.out.println(MASKED_WORD);
        System.out.println("Введите предполагаемую букву: ");
        do {
            String inputLetter = scanner.nextLine().toLowerCase();
            if (isLetterIsAlreadyOpen(inputLetter)) {
                System.out.println("Эта буква уже открыта, попробуйте еще раз.");
            } else if (inputLetter.length() > 1) {
                System.out.println("Введено больше одной буквы, попробуйте еще раз.");
            } else if (WRONG_LETTERS.contains(inputLetter)) {
                System.out.println("Вы уже проверяли эту букву, она не подходит. Попробуйте еще раз.");
            } else {
                return inputLetter.toLowerCase();
            }
        } while (true);
    }

    // Метод проверки, что указанная буква уже открыта
    public static boolean isLetterIsAlreadyOpen(String letter) {
        return MASKED_WORD.contains(letter);
    }

    // Метод обработки пользовательского ввода
    public static void identifyUserInput(String letter) {
        if (isLetterInWord(letter)) {
            openLettersInMaskedWord(getIndexesOfEqualLetters(letter));
        } else {
            ERROR_COUNT++;
            WRONG_LETTERS.add(letter);
            System.out.println("Этой буквы в слове нет. У вас осталось попыток: " + (ERROR_MAX_COUNT - ERROR_COUNT));
            printGallows(ERROR_COUNT);
        }
    }

    // Метод проверки наличия буквы в слове
    public static boolean isLetterInWord(String letter) {
        return MYSTERIOUS_WORD.contains(letter);
    }

    // Метод для отрисовки виселицы
    public static void printGallows(int ERROR_COUNT) {
        if (ERROR_COUNT == 0) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        } else if (ERROR_COUNT == 1) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "      " + "(_)" + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        } else if (ERROR_COUNT == 2) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "      " + "(_)" + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        } else if (ERROR_COUNT == 3) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "      " + "(_)" + "\n" +
                    "   " + (char) 124 + "     " + (char) 45 + (char) 45 + (char) 124 + "\n" +
                    "   " + (char) 124 + "    " + (char) 47 + "  " + (char) 124 + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        } else if (ERROR_COUNT == 4) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "      " + "(_)" + "\n" +
                    "   " + (char) 124 + "     " + (char) 45 + (char) 45 + (char) 124 + (char) 45 + (char) 45 + "\n" +
                    "   " + (char) 124 + "    " + (char) 47 + "  " + (char) 124 + "  " + (char) 92 + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        } else if (ERROR_COUNT == 5) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "      " + "(_)" + "\n" +
                    "   " + (char) 124 + "     " + (char) 45 + (char) 45 + (char) 124 + (char) 45 + (char) 45 + "\n" +
                    "   " + (char) 124 + "    " + (char) 47 + "  " + (char) 124 + "  " + (char) 92 + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "      " + (char) 47 + "\n" +
                    "   " + (char) 124 + "     " + (char) 47 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        } else if (ERROR_COUNT == 6) {
            System.out.println("    " + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + "\n" +
                    "   " + (char) 124 + (char) 47 + "      " + (char) 33 + "\n" +
                    "   " + (char) 124 + "      " + "(_)" + "\n" +
                    "   " + (char) 124 + "     " + (char) 45 + (char) 45 + (char) 124 + (char) 45 + (char) 45 + "\n" +
                    "   " + (char) 124 + "    " + (char) 47 + "  " + (char) 124 + "  " + (char) 92 + "\n" +
                    "   " + (char) 124 + "       " + (char) 124 + "\n" +
                    "   " + (char) 124 + "      " + (char) 47 + " " + (char) 92 + "\n" +
                    "   " + (char) 124 + "     " + (char) 47 + "   " + (char) 92 + "\n" +
                    "   " + (char) 124 + "\n" +
                    (char) 95 + (char) 95 + (char) 95 + (char) 124 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95 + (char) 95);
        }
    }

    // Метод обнуления данных для новой игры
    private static void clearInfo() {
        ERROR_COUNT = 0;
        WRONG_LETTERS.clear();
    }
}
