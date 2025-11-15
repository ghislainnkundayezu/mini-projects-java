package com.tictactoe;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


public class GameController {
    public final int MAX_ROUNDS = 5;
    public Board board;
    public AIAgent aiAgent;
    public Scanner input;
    public GameConstants.Player humanPlayer;
    public GameConstants.Player computerPlayer;
    public GameConstants.Player currentPlayer;
    public HashMap<GameConstants.Player, Integer> scoreBoard;  // keeping the score of each player per round

    public GameController() {
        this.board = new Board();
        this.aiAgent = new AIAgent();
        this.input = new Scanner(System.in);
        this.humanPlayer = null;
        this.computerPlayer = null;
        this.scoreBoard = new HashMap<>();
        this.scoreBoard.put(GameConstants.Player.X, 0);
        this.scoreBoard.put(GameConstants.Player.O, 0);
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
                this.input.nextLine();  // consume the end of the line

                if (choice == 1) {
                    this.handleGamePlay();
                }else if (choice == 2) {
                    quitGame = true;
                    System.out.println("\nIt was a pleasure to have you!\n");
                }else {
                    throw new IllegalArgumentException("Unknown choice");
                }
            }catch (Exception e) {
                if (e.getMessage()==null) {
                    System.out.printf("\n%s\n", "Wrong Input Type!");
                }
                else {
                    System.out.printf("\n%s\n", e.getMessage());
                }
                System.out.printf("\n%s", "Press Enter to continue...");
                if (this.input.hasNextLine()) {
                    this.input.nextLine();
                }
            }
        }
    }


    public void handleGamePlay() {
        boolean gameOver = false;
        int rounds = 1;

        System.out.print("\nWhich player do you want to choose X or O (You can Enter q to quit game)");
        System.out.print("\nEnter your choice: ");
        String playerChoice = this.input.nextLine();
        // assign players their respective symbols.
        if (playerChoice.equalsIgnoreCase("x")) {
            this.humanPlayer = GameConstants.Player.X;
            this.computerPlayer = GameConstants.Player.O;
        }else if (playerChoice.equalsIgnoreCase("o")) {
            this.humanPlayer = GameConstants.Player.O;
            this.computerPlayer = GameConstants.Player.X;
        }else if  (playerChoice.equalsIgnoreCase("q")) {
            gameOver = true;
        }else {
            throw new IllegalArgumentException("Unknown choice");
        }

        while (!gameOver) {
            // Indicate the current round.
            System.out.printf("\nWelcome to Round %d\n", rounds);

            // set the current player to X
            this.setCurrentPlayer();


            // start a round.
            boolean gameRoundOver = false;
            while (!gameRoundOver) {
                try {
                    // place the pieces on the board alternately by update the curr player.
                    if (this.currentPlayer == this.humanPlayer) {
                        System.out.print("\nPlay: ");
                        int position = this.input.nextInt();
                        this.input.nextLine();
                        this.board.play(this.humanPlayer, position);

                        // display the board after the human player makes a move.
                        this.board.printBoard();

                        // update the current player
                        this.currentPlayer = this.computerPlayer;
                    }else if (this.currentPlayer == this.computerPlayer) {
                        int bestPosition = this.aiAgent.play(
                                this.board,
                                this.computerPlayer,
                                this.humanPlayer);

                        // make the computer move on the board.
                        this.board.play(this.currentPlayer, bestPosition);

                        // display the board after the AI player makes a move.
                        // Add a sleep of like 2 seconds
                        System.out.print("\nAI Move...");
                        Thread.sleep(2000);
                        this.board.printBoard();

                        // update the current player
                        this.currentPlayer = this.humanPlayer;
                    }

                    // check if there is a win or a draw and the round is over.
                    GameConstants.GameState gameState = this.board.boardStatus();

                    if (gameState == GameConstants.GameState.WIN) {
                        gameRoundOver = true;
                        // update the score for each player per round
                        int currentScore = this.scoreBoard.get(this.board.winner);
                        this.scoreBoard.put(this.board.winner, currentScore + 1);
                        System.out.println("\nWinner is " + this.board.winner + "\n");
                        // print winner.
                    }else if (gameState == GameConstants.GameState.DRAW) {
                        gameRoundOver = true;
                        System.out.println("\nDraw!");
                    }
                } catch (Exception e) {
                    if (e.getMessage()==null) {
                        System.out.printf("\n%s\n", "Wrong Input Type: Position should be an Integer");
                    }else {
                        System.out.printf("\n%s\n", e.getMessage());
                    }
                    System.out.printf("\n%s", "Press Enter to continue...");
                    if (this.input.hasNextLine()) {
                        this.input.nextLine();
                    }
                }
            }
            // End Round and reset the board.
            this.board.resetBoardData();
            System.out.printf("\nThis the end of Round %d\n", rounds);
            // after each successful round update the score.
            rounds++;
            if (rounds > this.MAX_ROUNDS) {
                gameOver = true;
            }
        }

        // Determine winner.
        int x_score,  o_score;
        x_score = this.scoreBoard.get(this.currentPlayer);
        o_score = this.scoreBoard.get(this.currentPlayer);

        if (x_score > o_score) {
            System.out.println("\nX won the game!");
        }else {
            System.out.println("\nO Won the game!");
        }
    }

    private void setCurrentPlayer() {
        if (this.humanPlayer == GameConstants.Player.X) {
            this.currentPlayer = this.humanPlayer;
        }else if (this.humanPlayer == GameConstants.Player.O) {
            this.currentPlayer = this.computerPlayer;
        }
    }

}
