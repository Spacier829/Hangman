import java.io.IOException;
import java.util.*;
import java.io.File;

public class Main {
    private static final Random RANDOM = new Random();
    private static final Scanner SCANNER = new Scanner(System.in);

    // Загаданное слово
    private static String mysteriousWord;

    // Замаскированное слово
    private static String maskedWord;

    // Количество слов в словаре
    private static final int WORDS_COUNT = 51297;

    // Количество ошибок
    private static int errorCount = 0;

    // Максимальное число допустимых ошибок
    private static final int ERROR_MAX_COUNT = 6;

    // Список для хранения неверно введенных букв
    private static HashSet<Character> wrongLetters = new HashSet<>() {
    };

    public static void main(String[] args) {
        startGameOrExit();
        System.out.println("Спасибо за игру!");
    }

    // Метод выбора начала игры или выхода
    public static void startGameOrExit() {
        String userChoice;
        System.out.println("===========================================================");
        System.out.println("|                      Игра Виселица                      |");
        System.out.println("===========================================================");
        do {
            System.out.println("Введите 'старт' чтобы начать игру или 'выход', чтобы выйти:");
            userChoice = SCANNER.nextLine();
            if (userChoice.equals("старт")) {
                gameLoop();
            } else if (userChoice.equals("выход")) {
                return;
            } else {
                System.out.println("Некорректный ввод, попробуйте еще раз.");
            }
        } while (true);
    }

    // Основной цикл игры
    public static void gameLoop() {
        mysteriousWord = selectRandomWord();
        createMaskedWord(mysteriousWord);
        printGallows(errorCount);
        do {
            System.out.println(mysteriousWord);// для отладки
            identifyUserInput(userInputLetter());
        } while (errorCount != ERROR_MAX_COUNT && !maskedWord.equals(mysteriousWord));
        checkGameState();
        clearInfo();
    }

    // Метод проверки победы или поражения
    public static void checkGameState() {
        if (maskedWord.equals(mysteriousWord)) {
            System.out.println("Поздравляем! Вы победили!");
        } else {
            System.out.println("К сожалению Вы проиграли.");
            System.out.println("Загаданное слово: " + mysteriousWord);
        }
    }

    // Метод выбора случайного слова из словаря
    public static String selectRandomWord() {
        try (Scanner fileScanner = new Scanner(new File("./Dictionary/dictionary.txt"))) {
            if (fileScanner.hasNextLine()) {
                int wordLineID = RANDOM.nextInt(WORDS_COUNT);
                for (int i = 0; i < wordLineID; i++) {
                    fileScanner.nextLine();
                }
                mysteriousWord = fileScanner.nextLine().toLowerCase();
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return mysteriousWord;
    }

    // Метод создания замаскированного слова с 1 открытой буквой в качестве подсказки
    public static void createMaskedWord(String mysteriousWord) {
        StringBuilder maskedWord = new StringBuilder(mysteriousWord);
        int numberOfLettersOfWord = mysteriousWord.length();

        for (int maskedLetterID = 0; maskedLetterID < numberOfLettersOfWord; maskedLetterID++) {
            {
                maskedWord.setCharAt(maskedLetterID, '_');
            }
        }

        // Выбирается случайным образом индекс буквы, после чего эта буква открывается в замаскированном слове
        int helpLetterID = RANDOM.nextInt(numberOfLettersOfWord);
        ArrayList<Integer> indexesOfHelpLetters;
        indexesOfHelpLetters = getIndexesOfEqualLetters((Main.mysteriousWord.charAt(helpLetterID)));
        Main.maskedWord = maskedWord.toString().toLowerCase();
        openLetters(indexesOfHelpLetters);
    }

    // Метод возвращения индексов угаданных букв
    public static ArrayList<Integer> getIndexesOfEqualLetters(Character letter) {
        ArrayList<Integer> lettersIndexes = new ArrayList<>();
        int index = -1;
        do {
            index = mysteriousWord.indexOf(letter, index + 1);
            if (index != -1) {
                lettersIndexes.add(index);
            }
        } while (index != -1);
        return lettersIndexes;
    }

    // Метод открытия угаданных букв
    public static void openLetters(ArrayList<Integer> letterIndexes) {
        StringBuilder maskedWord = new StringBuilder(Main.maskedWord);
        for (int index : letterIndexes) {
            if (Main.maskedWord.charAt(index) != mysteriousWord.charAt(index)) {
                maskedWord.setCharAt(index, mysteriousWord.charAt(index));
            }
            Main.maskedWord = maskedWord.toString();
        }
    }

    // Пользовательский ввод буквы
    public static Character userInputLetter() {
        System.out.println(maskedWord);
        System.out.println("Введите предполагаемую букву: ");
        do {
            String inputLetter = SCANNER.nextLine().toLowerCase();
            if (inputLetter.isEmpty()) {
                System.out.println("Вы ничего не ввели, попробуйте еще раз.");
            }
            else if (isLetterIsAlreadyOpen(inputLetter.charAt(0))) {
                System.out.println("Эта буква уже открыта, попробуйте еще раз.");
            } else if (wrongLetters.contains(inputLetter.charAt(0))) {
                System.out.println("Вы уже проверяли эту букву, она не подходит. Попробуйте еще раз.");
            } else {
                return inputLetter.charAt(0);
            }
        } while (true);
    }

    // Метод проверки, что указанная буква уже открыта
    public static boolean isLetterIsAlreadyOpen(Character letter) {
        return maskedWord.contains(letter.toString());
    }

    // Метод обработки пользовательского ввода
    public static void identifyUserInput(Character letter) {
        if (isLetterInWord(letter)) {
            openLetters(getIndexesOfEqualLetters(letter));
        } else {
            errorCount++;
            wrongLetters.add(letter);
            System.out.println("Этой буквы в слове нет. У вас осталось попыток: " + (ERROR_MAX_COUNT - errorCount));
            printGallows(errorCount);
        }
    }

    // Метод проверки наличия буквы в слове
    public static boolean isLetterInWord(Character letter) {
        return mysteriousWord.contains(letter.toString());
    }

    // Метод для отрисовки виселицы
    public static void printGallows(int errorCount) {
        switch (errorCount) {
            case 0 -> System.out.println("""
                      ________
                      |/     !
                      |
                      |
                      |
                      |
                      |
                      |
                    --|---------
                    """);

            case 1 -> System.out.println("""
                      ________
                      |/     !
                      |     (_)
                      |
                      |
                      |
                      |
                      |
                      |
                    --|---------
                    """);

            case 2 -> System.out.println("""
                      ________
                      |/     !
                      |     (_)
                      |      |
                      |      |
                      |      |
                      |
                      |
                      |
                    --|---------
                    """);

            case 3 -> System.out.println("""
                      ________
                      |/     !
                      |     (_)
                      |    --|
                      |   /  |
                      |      |
                      |
                      |
                      |
                    --|---------
                    """);
            case 4 -> System.out.println("""
                      ________
                      |/     !
                      |     (_)
                      |    --|--
                      |   /  |  |
                      |      |
                      |
                      |
                      |
                    --|---------
                    """);
            case 5 -> System.out.println("""
                      ________
                      |/     !
                      |     (_)
                      |    --|--
                      |   /  |  |
                      |      |
                      |     /
                      |    /
                      |
                    --|---------
                    """);
            case 6 -> System.out.println("""
                      ________
                      |/     !
                      |     (_)
                      |    --|--
                      |   /  |  |
                      |      |
                      |     / |
                      |    /  |
                      |
                    --|---------
                    """);
        }
    }

    // Метод обнуления данных для новой игры
    private static void clearInfo() {
        errorCount = 0;
        wrongLetters.clear();
    }
}
