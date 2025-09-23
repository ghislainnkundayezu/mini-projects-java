package main.java.com.tictactoe;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello Tic Tac Toe!");

        GameController tictactoe = new GameController();
        tictactoe.start();
    }
}
