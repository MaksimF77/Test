package playinword;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameInWord {
    char[] field; // массив для игрового поля
    char[] searchWord; //слово которое надо найти
    private int size; //количество клеток поля (букв в слове)
    private GameInWord gameField;//игровое поле
    private String whoseMove;//чей ход
    Scanner scanner = new Scanner(System.in);
    private boolean gameOver = false;//проверка на окончание игры
    private boolean step = false;//если один игрок отгадает буквы подряд

    void setUpNewGame() {

        this.gameField = new GameInWord();// проинициализировал(создал) игровое поле gameField
        this.gameField.randomWord();
    }

    void newField() {
        this.field = new char[size];
        for (int i = 0; i < size; i++) {
            field[i] = '#';
        }
        this.printField();
    }

    void randomWord() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("randomWord.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String[] randomWords = new String[200];
        for (int i = 0; i < 200; i++) {
            try {
                randomWords[i] = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        int rw = new Random().nextInt(200);// сделал на 200, при увеличении добавить в файл слова
        String str = randomWords[rw];
        this.size = str.length();
        this.searchWord = str.toCharArray(); //слово которое надо найти
        System.out.println("Отгадайте слово: ");
        newField();
    }

    void printField() {
        for (int q = 0; q < size; q++) {
            System.out.print(this.field[q]);
        }
        System.out.println();
    }

     void play() {
        System.out.println("Введите имя первого игрока: ");
        String playerOne = this.scanner.nextLine();
        System.out.println("Введите имя второго игрока: ");
        String playerTwo = this.scanner.nextLine();
        int firstPlayer = new Random().nextInt(99); // случайный выбор кто первый ходит
        if (firstPlayer % 2 == 0) {
            this.whoseMove = playerOne;
        } else {
            this.whoseMove = playerTwo;
        }
        System.out.println("По случайному выбору, игру начинает " + this.whoseMove + " :)");
        setUpNewGame();
        while (!gameOver) {
            this.makeMove();
            this.gameOver = this.gameField.isGameOver();
            if (this.gameOver) {
                System.out.println(this.whoseMove + " Вы выиграли!!!");
            }
            if (!step) {
                if (this.whoseMove.equals(playerOne)) {
                    this.whoseMove = playerTwo;
                } else {
                    this.whoseMove = playerOne;
                }
            }
        }
        System.out.println("Game over!");
    }

    private void makeMove() {
        step = false;
        System.out.print(this.whoseMove + " Угадаете слово целиком? да/нет: ");
        String yesNo = this.scanner.nextLine();
        if (yesNo.equals("да")) {
            wholeWordCheck();
        } else {
            wordSpellCheck(this.gameField.size);
        }
    }

    private void wordSpellCheck(int size) {//угадывание слова по буквам
        System.out.print("Введите букву: ");
        char letter = this.gameField.scanner.nextLine().charAt(0);
        for (int i = 0; i < size; i++) {
            if (this.gameField.isPlaceFree(i)) {
                if (this.gameField.searchWord[i] == letter) {
                    whoField(i, letter);
                    step = true;
                }
            }
        }
        this.gameField.printField();
    }

    private void wholeWordCheck() {//угадывание слова целиком
        System.out.print("Введите слово: ");
        String whole = this.gameField.scanner.nextLine();//this.gameField.scanner.nextLine();
        char[] guessedWord;
        guessedWord = whole.toCharArray();
        if (Arrays.equals(guessedWord, this.gameField.searchWord)) {
            System.out.println("Поздравляю, Вы угадали слово!");
            this.gameField.field = guessedWord;
        } else {
            System.out.print(this.whoseMove + " Вы не угадали слово (: ");
            System.out.println();
            this.gameField.printField();
        }
    }

    void whoField(int index, char let) {// меняю значение пустой ячейки на угаданную букву
        this.gameField.field[index] = let;
    }

    boolean isPlaceFree(int index) {// определяет свободная ли клеточка
        return this.field[index] == '#';
    }

    private boolean isGameOver() {//проверка на совпадение слова с искомым
        return Arrays.equals(this.field, this.searchWord);
    }
}
