package com.tictactoe;

import java.util.ArrayList;

public class AIAgent {
    public GameConstants.Player maxPlayer;
    public GameConstants.Player minPlayer;
    public Board currentBoard;

    public AIAgent() {
        this.maxPlayer = null;
        this.minPlayer = null;
        this.currentBoard = null;
    }

    public int play(Board gameBoard, GameConstants.Player maxPlayer, GameConstants.Player minPlayer) {
        if (gameBoard == null || maxPlayer == null || minPlayer == null) {
            throw new IllegalArgumentException("Missing Arguments");
        }
        if (maxPlayer == minPlayer) {
            throw new IllegalArgumentException("The max player can't be equal to the min player");
        }

        this.currentBoard = this.copyBoard(gameBoard);
        this.maxPlayer = maxPlayer;
        this.minPlayer = minPlayer;

        return getBestMove();
    }

    public int getBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestPosition = -1;

        for (int pos : this.getAvailablePositions()) {
            // play the piece in the position.
            this.currentBoard.play(this.maxPlayer, pos);

            // get the score
            int score = minimaxScore();

            // undo move
            this.currentBoard.undoRecentMove();

            // update the bestScore
            if (score > bestScore) {
                bestPosition = pos;
                bestScore = score;
            }
        }

        return bestPosition;
    }

    // returns the overall best score of a move by anticipating future moves.
    public int minimaxScore() {
        if (terminalState()) {
            return getScore();
        }

        GameConstants.Player nextPlayer =
                (this.currentBoard.activePlayer == this.maxPlayer)
                        ? this.minPlayer
                        : this.maxPlayer;

        int bestValue = (nextPlayer == this.maxPlayer)
                ? Integer.MIN_VALUE
                : Integer.MAX_VALUE;

        for (int position : this.getAvailablePositions()) {
            // place the piece in the position
            this.currentBoard.play(
                    nextPlayer,
                    position);
            // call the func recursively and get the score.
            int score = minimaxScore();

            // undo the move
            this.currentBoard.undoRecentMove();

            // update the best value with the max.
            if (nextPlayer == this.maxPlayer) {
                bestValue = Math.max(bestValue, score);
            } else {
                bestValue = Math.min(bestValue, score);
            }

        }
        return bestValue;
    }

    // the BoardState is -1 when the min player wins, 1 when the max player wins and 0 for a draw.
    public boolean terminalState() {
        GameConstants.GameState state = this.currentBoard.boardStatus();

        return state != GameConstants.GameState.IN_PROGRESS;
    }

    // gives a score to the board if it's in a terminal state.
    public int getScore() {
        if (this.currentBoard.boardStatus() == GameConstants.GameState.IN_PROGRESS) {
            throw new IllegalStateException("Cannot evaluate: the game is still in progress.");
        }

        if (this.currentBoard.winner == this.maxPlayer) {
            return 1;
        } else if (this.currentBoard.winner == this.minPlayer) {
            return -1;
        }
        return 0;
    }

    public ArrayList<Integer> getAvailablePositions() {
        ArrayList<Integer> availablePositions = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (!this.currentBoard.occupiedPositions.contains(i)) {
                availablePositions.add(i);
            }
        }
        return availablePositions;
    }

    public Board copyBoard(Board board) {
        if (board == null) {
            throw new NullPointerException("Board is null");
        }

        return new Board(board);
    }
}
