package playinword;

import java.io.IOException;

public class WordGame {
    public static void main(String[] args) throws IOException {

//        "Игра в слова"
//      *  Генерирую 200 случайных слов на сайте-генераторе и сохранил их в файл.
//      *  Из файла считываю случайное слово, и вывод на экран в закрытом виде #####…
//      *  Игроки по очереди отгадывают букву.
//      *  Если игрок отгадал букву, то он продолжает ход
//      *  Можно угадать слово целиком

        System.out.println("Начнем новую игру!");
        GameInWord gameInWord = new GameInWord();
        gameInWord.play();
    }
}
