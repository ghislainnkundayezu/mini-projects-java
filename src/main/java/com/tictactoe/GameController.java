package com.tictactoe;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Count wins for each player throughout the four rounds.
 *
 /*
 */

public class GameController {
    private final int MAX_ROUNDS = 4;
    private int rounds;
    private Board board;
    private AIAgent aiAgent;
    private GameConstants.Player humanPlayer;
    private GameConstants.Player aiPlayer;
    private GameConstants.Player currentPlayer;
    private HashMap<String, Integer> scores;
    private Scanner input = new Scanner(System.in);


    public GameController() {
        this.board = new Board();
        this.aiAgent = new AIAgent();
        this.rounds = 0;
        this.scores = new HashMap<>();
        this.scores.put("human", 0);
        this.scores.put("ai", 0);

    }

    public void start() {
        boolean running = true;

        while (running) {
            homeInstructions();
            System.out.print("Enter an number for your choice(1-3): ");
            try{
                int choice = this.input.nextInt();
                input.nextLine();            // We gotta consume the extra new line char("/n")
                System.out.println("\n");

                switch (choice) {
                    case 1:
                        runGame();
                        continue;
                    case 2:
                        gameManual();
                        continue;
                    case 3:
                        System.out.println("Thanks for playing!");
                        running = false;
                        break;
                    default:
                        throw new IllegalArgumentException("The choice must be between 1 and 3");

                }

            } catch(InputMismatchException e) {
                System.out.println("Invalid input type");
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void exit() {
        System.out.println("Exiting game...");
    }

    /**
     A function that controls the whole game like:
     * Like managing the four rounds of the game.
     * Managing players' turns.
     * interacting with the board, e.g printing the board, updating it, validating it.
     *
     * */
    // TODO Add the option to for the user to return back to the Home Screen.
    public void runGame() {
        boolean gameOver = false;

        // human has to select between O and X.
        // if the human select X they begin and vice versa.

        while (!gameOver && MAX_ROUNDS > rounds) {
            System.out.println("Which Piece do you want to use? (X or O)  <---->  ");
            String humanPlayer = this.input.nextLine();

            if (humanPlayer.equalsIgnoreCase("X")) {
                this.humanPlayer = GameConstants.Player.X;
                this.aiPlayer = GameConstants.Player.O;
                this.currentPlayer = this.humanPlayer;
            }else if (humanPlayer.equalsIgnoreCase("O")){
                this.aiPlayer = GameConstants.Player.X;
                this.humanPlayer = GameConstants.Player.O;
                this.currentPlayer = this.aiPlayer;
            }else {
                throw new IllegalArgumentException("Invalid input type");
            }

            System.out.println("Player X is going to start...");

            // while the round is still going on
            // if the current player equals the human player prompt for a position
            // else the current player equals the ai player prompt use the best move method to obtain the best move for the ai.
            // check the board status after a player plays and determine if the round is still going on or over.
            // if the game is still going on switch the players
            boolean isRoundOver = false;

            while (!isRoundOver) {
                int move;
                if (this.currentPlayer.equals(this.humanPlayer)) {
                    move = this.input.nextInt();
                    this.input.nextLine();
                    this.board.play(this.currentPlayer, move);
                }else if (this.currentPlayer.equals(this.aiPlayer)) {
                    /* move = this.aiAgent.play(
                            this.board,
                            this.aiPlayer,
                            this.humanPlayer);*/
                    //this.board.play(this.currentPlayer, move);

                    // TODO After Each play we have to print the board.

                }else {
                    // If there is an error we restart.
                    throw new IllegalArgumentException("System Error: Invalid Player Symbol");

                }
            }



            // increase the score of the winner in the scores HashMap.
            rounds++;
        }


    }

    private void homeInstructions() {
        System.out.println("Welcome to TicTacToe!");
        System.out.println("1. Start game");
        System.out.println("2. Read game manual");
        System.out.println("3. Key commands");
        // start game.
        // read game instructions.
        // controls (q for quit and h for home)
    }

    private void gameManual() {

    }


}
