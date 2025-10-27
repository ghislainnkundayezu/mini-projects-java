package com.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Board {
    public int count_X;
    public int count_O;
    private int[][] board;
    public GameConstants.Player activePlayer;
    public GameConstants.Player winner;
    public GameConstants.Player looser;
    public ArrayList<Integer> occupiedPositions;
    public ArrayList<Integer> moves;    // stores the sequential order of how pieces were placed on the board.

    // default constructor
    public Board() {
        this.count_X = 0;
        this.count_O = 0;
        this.activePlayer = null;
        this.board = new int[3][3];
        this.winner = null;
        this.looser = null;
        this.occupiedPositions = new ArrayList<>();
        this.moves = new ArrayList<>();
    }

    // copy constructor for copying current board data to a new board.
    public Board(Board originalBoard) {
        this.count_X = originalBoard.count_X;
        this.count_O = originalBoard.count_O;
        this.activePlayer = originalBoard.activePlayer;
        this.winner = originalBoard.winner;
        this.looser = originalBoard.looser;
        this.occupiedPositions = new ArrayList<>(originalBoard.occupiedPositions);
        this.moves = new ArrayList<>(originalBoard.moves);
        this.board = new int[originalBoard.board.length][];

        for (int i = 0; i < this.board.length; i++) {
            this.board[i] = originalBoard.board[i].clone();
        }
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
        } else {
            count_O++;
        }
    }

    public void resetBoardData() {
        this.board = new int[3][3];
        count_X = 0;
        count_O = 0;
        activePlayer = null;
        winner = null;
        looser = null;
        occupiedPositions = new ArrayList<>();
        moves = new ArrayList<>();
    }

    public GameConstants.GameState boardStatus() {
        if (boardHasWinner()) {
            return GameConstants.GameState.WIN;
        } else if (boardHasDraw()) {
            return GameConstants.GameState.DRAW;
        } else {
            return GameConstants.GameState.IN_PROGRESS;
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

    public void undoRecentMove() {
        if (this.occupiedPositions.isEmpty() || this.moves.isEmpty()) {
            throw new IllegalStateException("The Board is still empty");
        }

        // remove the position and move from the arrays.
        int position = this.occupiedPositions.removeLast();
        int prevPlayer = this.moves.removeLast();

        // update the board.
        int row = (position - 1) / 3;
        int col = (position - 1) % 3;
        this.board[row][col] = 0;

        // update the count of the corresponding player
        if (prevPlayer == 1) {
            this.count_X--;
        }else {
            this.count_O--;
        }

        // update the current player.
        if (this.moves.isEmpty()) {
            this.activePlayer = null;
        }else {
            this.activePlayer = GameConstants.Player.fromValue(this.moves.getLast());
        }

        this.winner = null;
        this.looser = null;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Board otherBoard)) {
            return false;
        }

        // compare counts of X and O.
        if ((this.count_X != otherBoard.count_X) || (this.count_O != otherBoard.count_O)) {
            return false;
        }

        // compare activePlayer field.
        if (this.activePlayer != otherBoard.activePlayer) {
            return false;
        }

        // compare looser and winner fields
        if ((this.winner != otherBoard.winner) || (this.looser != otherBoard.looser)) {
            return false;
        }

        // compare arraylists
        if (!(Objects.equals(this.occupiedPositions, otherBoard.occupiedPositions)) ||
                !(Objects.equals(this.moves, otherBoard.moves))) {
            return false;
        }

        // compare the board array
        if (!Arrays.deepEquals(this.board, otherBoard.board)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                count_X,
                count_O,
                activePlayer,
                winner,
                looser,
                Arrays.deepHashCode(board),
                occupiedPositions,
                moves
        );
    }

}
