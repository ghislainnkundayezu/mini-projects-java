package main.java.com.tictactoe;

import java.util.HashMap;

/**
 What I want the board class to be able to do:
 * I want it to print the board.
 * I want it to validate the board to check for a win, a loss, a draw, or if the players can still play.
 * I want it to update the board
 *
 */

public class Board {
    private int[][] board;

    public HashMap<String, Integer> gamePieces = new HashMap<>();

    public Board() {
        board = new int[3][3];
        gamePieces.put("x", 1);
        gamePieces.put("o", 2);
    }

    private String pieceRepr(int piece) {
        if (piece == 1) {
            return "X";
        }else if (piece == 2) {
            return "O";
        }else {
            return "-";
        }
    }

    public void printBoard() {
        System.out.print("\n");
        for (int[] row : this.board) {
            System.out.print("\t\t");
            for (int item : row) {
                System.out.printf("\t\t%s", pieceRepr(item));
            }
            System.out.print("\n\n");
        }
        System.out.print("\n");
    }

    public void play(String piece, int position) {

    }

    public void updateBoard(String piece, int position) {
        try {
            if (position < 1 || position > 9) {
                throw new IllegalArgumentException("Invalid position");
            }

            int row = (int) (position - 1) / 3;
            int col = (position - 1) % 3;

            this.board[row][col] = gamePieces.get(piece);

        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetBoard() {
        this.board = new int[3][3];
    }

    public void boardStatus() {

    }
}
