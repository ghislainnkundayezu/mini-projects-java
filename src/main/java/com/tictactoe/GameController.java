package com.tictactoe;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 */

public class GameController {
    public final int MAX_ROUNDS = 5;
    public Board board;
    public AIAgent aiAgent;
    public Scanner input;
    public GameConstants.Player humanPlayer;
    public GameConstants.Player computerPlayer;

    public GameController() {
        this.board = new Board();
        this.aiAgent = new AIAgent();
        this.input = new Scanner(System.in);
        this.humanPlayer = null;
        this.computerPlayer = null;
    }

    public void start() {
        boolean quitGame = false;

        // start the main loop of the game.
        while (!quitGame) {
            // print instructions
            System.out.print("\nWelcome to TicTacToe!\n");
            System.out.print("\n1. Start Game.\n");
            System.out.print("\n2. Quit Game.\n");

            try {
                System.out.print("\nEnter your choice: ");
                int choice = this.input.nextInt();
                input.nextLine();  // consume the end of the line

                if (choice == 1) {
                    this.handleGamePlay();
                }else if (choice == 2) {
                    quitGame = true;
                    System.out.println("\nIt was a pleasure to have you!\n");
                }else {
                    throw new IllegalArgumentException();
                }
            }catch (Exception e) {
                System.out.println("\nWrong choice. Try again.\n");
            }
        }
    }


    /**
    func for handling the game:
     -  human player chooses the player symbol(X or O)
     - if X player begins.
     - Manage turns of the players.
     - Manage the game for up to five rounds
     - The human player may exit the game. Go back to the dashboard.
     - Handle errors gracefully.
     - Pause for a second before displaying the move by the computer.
     - Always print to the terminal a player's turn to play.
     */

    public void handleGamePlay() {
        boolean gameOver = false;

        while (!gameOver) {

        }
    }

}
