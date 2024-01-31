import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.io.File;

public class Main {
    // Обозначения переменных глобальных


    private static Random random = new Random();

    public static void main(String[] args) {
//        запуск основного цикла игры
        selectWordFromDictionary();
    }

    // меню игры (начать, выйти из игры)
    public static void gameMenu() {

    }

    // основной цикл игры
    public static void gameLoop() {
    }

    // выбор слова для игры из словаря
    public static void selectWordFromDictionary() {
        try (Scanner fileScanner = new Scanner(new File("./Dictionary/dictionary.txt"))) {

            if (fileScanner.hasNextLine()) {
                int jumpCount = random.nextInt(51297);
//                String test = fileScanner.nextLine();
                for (int i = 0; i < jumpCount; i++) {
                    fileScanner.nextLine();
                }
                String test = fileScanner.nextLine();
                int a = 123;
            }
        } catch (IOException exception) {
            exception.printStackTrace(System.out);
        }
    }


    // выбор 2 случаных букв из слова
    public static void openTwoCharsOfWord() {

    }

    // Пользовательский ввод буквы
    // (проверка на дебила и т.д. пока не знаю)
    public static void inputChar() {

    }

    // Проверка наличия буквы в слове
    public static void checkCharFromWord() {

    }

    // отрисовка слова
    public static void printMysteryWord() {

    }

    // отрисовка виселицы
    public static void printGallows() {

    }


}