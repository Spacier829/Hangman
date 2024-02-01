import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Main {
    // Обозначения переменных глобальных
    private static Random random = new Random();

    private static String MYSTERIUOS_WORD;
    private static int WORDS_COUNT = 51297;

    public static void main(String[] args) {
//        запуск основного цикла игры
        String test = createMaskedWord(selectRandomWordFromDictionary());
        System.out.println(test);
        int a = 12;
    }

    // меню игры (начать, выйти из игры)
    public static void gameMenu() {

    }

    // основной цикл игры
    public static void gameLoop() {
    }

    // выбор слова для игры из словаря
    public static String selectRandomWordFromDictionary() {
        try (Scanner fileScanner = new Scanner(new File("./Dictionary/dictionary.txt"))) {
            if (fileScanner.hasNextLine()) {
                int wordLineId = random.nextInt(WORDS_COUNT);
                for (int i = 0; i < wordLineId; i++) {
                    fileScanner.nextLine();
                }
                MYSTERIUOS_WORD = fileScanner.nextLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
        return MYSTERIUOS_WORD;
    }


    // выбор 2 случайных букв из слова
    public static String createMaskedWord(String mysteriousWord) {
        StringBuilder maskedWord = new StringBuilder(mysteriousWord);
        int lettersCountByWord = mysteriousWord.length();
        int firstOpenLetterID = random.nextInt(lettersCountByWord);
        int secondOpenLetterID;
        do {
            secondOpenLetterID = random.nextInt(lettersCountByWord);
        } while (secondOpenLetterID == firstOpenLetterID);

        for (int maskedCharID = 0; maskedCharID < lettersCountByWord; maskedCharID++) {
            if (maskedCharID != firstOpenLetterID && maskedCharID!= secondOpenLetterID) {
                maskedWord.setCharAt(maskedCharID, '_');
            }
        }
         return maskedWord.toString();
    }

    // Пользовательский ввод буквы
    // (проверка на дебила и т.д. пока не знаю)
    public static void inputChar() {

    }

    // Проверка наличия буквы в слове
    public static void checkCharFromWord(char userChar) {
    }

    // Открытие символов в слове, если правильная буква
    public static void openCharsOfMaskedWord() {

    }

    // отрисовка слова
    public static void printMaskedMysteryWord(String maskedWord) {
        System.out.println(maskedWord);
    }

    // отрисовка виселицы
    public static void printGallows() {

    }


}