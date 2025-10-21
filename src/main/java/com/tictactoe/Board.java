package com.tictactoe;

import java.util.ArrayList;


/**
 What I want the board class to be able to do:
 * I want it to print the board.
 * I want it to validate the board to check for a win, a loss, a draw, or if the players can still play.
 * I want it to update the board
 *
 */

public class Board {
    private int[][] board;
    public GameConstants.Player activePlayer;
    public GameConstants.Player winner;
    public GameConstants.Player looser;
    public int count_X;
    public int count_O;
    public ArrayList<Integer> occupiedPositions;
    public ArrayList<Integer> moves;


    public Board() {
        count_X = 0;
        count_O = 0;
        activePlayer = null;
        board = new int[3][3];
        winner = null;
        looser = null;
        occupiedPositions = new ArrayList<>();
        moves = new ArrayList<>();
    }

    public void play(GameConstants.Player player, int position) {
        if (position < 1 || position > 9) {
            throw new IllegalArgumentException("Invalid position");
        }

        if (this.occupiedPositions.contains(position)) {
            throw new IllegalArgumentException("Position is already occupied");
        }

        if (!this.moves.isEmpty() && this.moves.getLast().equals(player.getValue())) {
            throw new IllegalArgumentException("This Player cannot twice before their opponent plays.");
        }

        // update the active piece
        this.activePlayer = player;

        // if no errors update the board.
        this.updateBoard(player, position);
    }


    public void printBoard() {
        System.out.print("\n");
        for (int[] row : this.board) {
            System.out.print("\t\t");
            for (int item : row) {
                System.out.printf("\t\t%s", GameConstants.Player.fromValue(item));
            }
            System.out.print("\n\n");
        }
        System.out.print("\n");
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void updateBoard(GameConstants.Player player, int position) {
        int row = (int) (position - 1) / 3;
        int col = (position - 1) % 3;

        this.board[row][col] = player.getValue();

        this.moves.add(player.getValue());
        this.occupiedPositions.add(position);

        // update the count of pieces
        if (player.name().equalsIgnoreCase("x")) {
            count_X++;
        }else {
            count_O++;
        }
    }

    public void resetBoardData() {
        this.board = new int[3][3];
        count_X = 0;
        count_O = 0;
        activePlayer = null;
        winner = null;
        looser  = null;
        occupiedPositions = new ArrayList<>();
        moves = new ArrayList<>();
        // TODO we also have to reset other variables that we created.
    }

    public int boardStatus() {
        if (boardHasWinner()) {
            return 1;
        }else if (boardHasDraw()) {
            return 0;
        }else {
            return -1;
        }
    }

    private boolean boardHasWinner() {
        // check rows
        for (int[] row : this.board) {
             if (row[0] != 0 && row[0] == row[1] && row[0] == row[2] && row[1] == activePlayer.getValue()) {
                 this.winner = row[0] == 1 ? GameConstants.Player.X : GameConstants.Player.O;
                 this.looser = this.winner == GameConstants.Player.X ? GameConstants.Player.O : GameConstants.Player.X;
                 return true;
            }
        }

        // check cols
        for (int col = 0; col < 3; col++) {
            if (board[0][col] != 0 && board[0][col] == board[1][col] &&
                    board[0][col] == board[2][col] && board[0][col] == activePlayer.getValue()) {

                this.winner = board[0][col] == 1 ? GameConstants.Player.X : GameConstants.Player.O;
                this.looser = this.winner == GameConstants.Player.X ? GameConstants.Player.O : GameConstants.Player.X;
                return true;
            }
        }

        // check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] &&
                board[0][0] == board[2][2] && board[0][0] == activePlayer.getValue()) {

            this.winner = board[0][0] == 1 ? GameConstants.Player.X : GameConstants.Player.O;
            this.looser = this.winner == GameConstants.Player.X ? GameConstants.Player.O : GameConstants.Player.X;
            return true;
        }

        // check anti-diagonals
        if (board[0][2] != 0 && board[0][2] == board[1][1] &&
                board[0][2] == board[2][0] && board[0][2] == activePlayer.getValue()) {

            this.winner = board[0][2] == 1 ? GameConstants.Player.X : GameConstants.Player.O;
            this.looser = this.winner == GameConstants.Player.X ? GameConstants.Player.O : GameConstants.Player.X;
            return true;
        }

        return false;
    }

    private boolean boardHasDraw() {
        boolean boardIsFull = this.count_X + this.count_O == 9;
        return boardIsFull && !boardHasWinner();
    }
}
