package com.tictactoe;

public class AIAgent {
    private int maxPlayer;
    private int minPlayer;
    private Board actualBoard;

    public AIAgent() {
        this.maxPlayer = 0;
        this.minPlayer = 0;
        this.actualBoard = null;
    }

    public void play(Board gameBoard, int maxPlayer, int minPlayer) {
        this.actualBoard = this.copyBoard(gameBoard);
        this.maxPlayer = maxPlayer;
        this.minPlayer = minPlayer;

    }

    private void bestMove() {
        if (this.actualBoard == null) {
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

    // return the BoardState -1 when the min player wins, 1 when the max player wins and 0 for a draw.
    private void evaluationFunction() {

    }

    private void getAvailablePositions(Board board) {

    }

    private Board copyBoard(Board board) {
        return null;
    }
}
