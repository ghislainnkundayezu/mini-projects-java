package main.java.com.tictactoe;

/**
 * Count wins for each player throughout the four rounds.
 *
 */
public class GameController {
    private Board board;
    private AIAgent ai;
    private int round;


    public GameController() {
        board = new Board();
        ai = new AIAgent(board);
        round = 0;
    }

    public void start() {
        System.out.println("Starting game...");
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
    public void handleGameLogic() {

    }

}
