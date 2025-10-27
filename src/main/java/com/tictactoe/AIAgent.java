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

    // TODO catch exceptions arising from improper inputs.
    public int getBestMove(Board gameBoard, GameConstants.Player maxPlayer, GameConstants.Player minPlayer) {
        this.currentBoard = this.copyBoard(gameBoard);
        this.maxPlayer = maxPlayer;
        this.minPlayer = minPlayer;

        return 0;
    }


    private void minimax() {
        // if terminal state return result 1(win of the ai), 0(for draw), -1(win of the opponent)

        // get a list of unexplored nodes.

        // if maximizing player
        // explore the
        // after exploring the position make it zero again

        // if not maximizing player
        // after exploring the position make it zero again

        /** Pseudocode

         func Minimax(board):
            if Terminal(board):
                return Value(board)

            if Player(board) == MAX:
                value = -Infinity
                for action in Actions(board):
                    value = Max(value, Minimax(Result(s, a))
                return value
            if Player(board) == MIN:
                 value = Infinity
                 for action in Actions(board):
                    value = Min(value, Minimax(Result(s, a))
                 return value

         func Result(s, a):
            s.play(a)
            return board.getBoard()
         * */
    }

    // the BoardState is -1 when the min player wins, 1 when the max player wins and 0 for a draw.
    public boolean terminalState() {
        GameConstants.GameState state = this.currentBoard.boardStatus();

        return state != GameConstants.GameState.IN_PROGRESS;
    }

    public ArrayList<Integer> getAvailablePositions() {
        ArrayList<Integer> availablePositions = new ArrayList<>();
        for (int i=1; i <= 9; i++ ) {
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
