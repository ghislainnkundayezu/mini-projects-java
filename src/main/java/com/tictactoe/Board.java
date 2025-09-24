package main.java.com.tictactoe;

import java.util.HashMap;
import java.util.HashSet;

/**
 What I want the board class to be able to do:
 * I want it to print the board.
 * I want it to validate the board to check for a win, a loss, a draw, or if the players can still play.
 * I want it to update the board
 *
 */

public class Board {
    private int[][] board;
    private int activePiece;
    private int count_X;
    private int count_O;
    private int winner;
    private HashSet<Integer> occupiedPositions;

    public HashMap<String, Integer> gamePieces = new HashMap<>();

    public Board() {
        count_X = 0;
        count_O = 0;
        activePiece = 0;
        winner = 0;
        occupiedPositions = new HashSet<>();
        board = new int[3][3];
        gamePieces.put("x", 1);
        gamePieces.put("o", 2);
    }

    public void play(String piece, int position) {
        // update the count of pieces
        if (piece.equals("x")) {
            count_X++;
        }else {
            count_O++;
        }

        // update the active piece
        this.activePiece = gamePieces.get(piece);

        // update the board
        this.updateBoard(piece, position);

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

    public void updateBoard(String piece, int position) {
        try {
            if (position < 1 || position > 9) {
                throw new IllegalArgumentException("Invalid position");
            }

            if (this.occupiedPositions.contains(position)) {
                throw new IllegalArgumentException("Position is already occupied");
            }

            int row = (int) (position - 1) / 3;
            int col = (position - 1) % 3;

            this.board[row][col] = gamePieces.get(piece);

            this.printBoard();

        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void resetBoard() {
        this.board = new int[3][3];
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

    public String getWinner() {
        if (this.winner == 1) {
            return "x";
        }
        if (this.winner == 2) {
            return "o";
        }

        return null;
    }

    private boolean boardHasWinner() {
        // check rows
        for (int[] row : this.board) {
             if (row[0] != 0 && row[0] == row[1] && row[0] == row[2] && row[1] == activePiece) {
                 this.winner = row[0];
                 return true;
            }
        }

        // check cols
        for (int col = 0; col < 3; col++) {
            if (board[0][col] != 0 && board[0][col] == board[1][col] &&
                    board[0][col] == board[2][col] && board[0][col] == activePiece) {

                this.winner = board[0][col];
                return true;
            }
        }

        // check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] &&
                board[0][0] == board[2][2] && board[0][0] == activePiece) {

            this.winner = board[0][0];
            return true;
        }

        // check anti-diagonals
        if (board[0][2] != 0 && board[0][2] == board[1][1] &&
                board[0][2] == board[2][0] && board[0][2] == activePiece) {

            this.winner = board[0][2];
            return true;
        }

        return false;
    }

    private boolean boardHasDraw() {
        boolean boardIsFull = this.count_X + this.count_O == 9;
        return boardIsFull && !boardHasWinner();
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
}
