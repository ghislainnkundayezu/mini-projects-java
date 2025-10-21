package com.tictactoe;

public class AIAgent {
    private int maxPlayer;
    private int minPlayer;
    private Board testBoard;

    public AIAgent() {
        this.maxPlayer = 0;
        this.minPlayer = 0;
        this.testBoard = null;
    }

    public void play(Board gameBoard, int maxPlayer, int minPlayer) {
        this.testBoard = this.copyBoard(gameBoard);
        this.maxPlayer = maxPlayer;
        this.minPlayer = minPlayer;

    }

    private void bestMove() {
        if (this.testBoard == null) {
            // TODO throw some weird exception
        }

        System.out.println("This is the best move");
    }

    private void minimax() {
        // if terminal state return result 1(win of the ai), 0(for draw), -1(win of the opponent)

        // get a list of unexplored nodes.

        // if maximizing player
        // explore the
        // after exploring the position make it zero again

        // if not maximizing player
        // after exploring the position make it zero again
    }

    private void getFreeSpots(Board board) {

    }

    private Board copyBoard(Board board) {
        return null;
    }
}
