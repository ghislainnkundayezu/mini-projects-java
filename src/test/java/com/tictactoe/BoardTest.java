package com.tictactoe;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static final Logger logger = Logger.getLogger(BoardTest.class.getName());

    private Board testBoard;
    private int testValidPosition;
    private int testInvalidPosition;
    private GameConstants.Player testPlayerX;
    private GameConstants.Player testPlayerO;

    @BeforeEach
    void setUp() {
        // runs BEFORE each test method
        testBoard = new Board();
        testValidPosition = 5;
        testInvalidPosition = 11;
        testPlayerX = GameConstants.Player.X;
        testPlayerO = GameConstants.Player.O;
    }

    @AfterEach
    void tearDown() {
        // runs AFTER each test method
        testBoard.resetBoardData();
        testBoard = null;
        testValidPosition = -1;
        testInvalidPosition = -1;
        testPlayerX = null;
        testPlayerO = null;
    }

    @Test
    void boardStartsEmpty() {
        int[][] result = this.testBoard.getBoard();
        int[][] expected = new int[3][3];

        assertArrayEquals(result, expected);
        logger.info("✅ testBoardStartsEmpty passed successfully");
    }

    @Test
    void updatesBoardDataSuccessfully() {
        testBoard.updateBoard(testPlayerX, testValidPosition);
        int[][] result = testBoard.getBoard();

        int[][] expected = new int[3][3];
        int row = (testValidPosition - 1) / 3;
        int col = (testValidPosition - 1) % 3;
        expected[row][col] = testPlayerX.getValue();

        // Test if the player was place in the correct position.
        assertArrayEquals(result, expected);

        // Test if the position was added to the occupied positions array.
        assertTrue(testBoard.occupiedPositions.contains(testValidPosition));

        // Test if the player was added to the moves array.
        assertTrue(testBoard.moves.contains(testPlayerX.getValue()));

        // Test if the count the corresponding player increased.
        assertEquals(1, testBoard.count_X);
        assertEquals(0, testBoard.count_O);

        logger.info("✅ updatesBoardDataSuccessfully passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfPositionIsInvalid() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.testBoard.play(testPlayerX, testInvalidPosition)
        );

        String expectedErrorMessage = "Invalid Position";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfPositionIsInvalid passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfPositionIsOccupied() {
        this.testBoard.play(testPlayerX, testValidPosition);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.testBoard.play(testPlayerO, testValidPosition)
        );

        String expectedErrorMessage = "Position is already occupied";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfPositionIsOccupied passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfTheSamePlayerPlaysTwice() {
        this.testBoard.play(testPlayerO, testValidPosition);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.testBoard.play(testPlayerO, 6)
        );

        String expectedErrorMessage = "This Player cannot twice before their opponent plays.";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfTheSamePlayerPlaysTwice passed successfully");
    }

    @Test
    void shouldReturnCorrectBoardStatusOnWin() {
        simulateWinGame(this.testBoard);

        GameConstants.GameState actualStatus = this.testBoard.boardStatus();
        GameConstants.GameState expectedStatus = GameConstants.GameState.WIN;

        assertEquals(expectedStatus, actualStatus);
        logger.info("✅ shouldReturnCorrectBoardStatusOnWin passed successfully");
    }

    @Test
    void shouldReturnCorrectBoardStatusOnDraw() {
        this.testBoard.play(testPlayerX, 1);
        this.testBoard.play(testPlayerO, 5);
        this.testBoard.play(testPlayerX, 4);
        this.testBoard.play(testPlayerO, 7);
        this.testBoard.play(testPlayerX, 3);
        this.testBoard.play(testPlayerO, 2);
        this.testBoard.play(testPlayerX, 8);
        this.testBoard.play(testPlayerO, 6);
        this.testBoard.play(testPlayerX, 9);

        GameConstants.GameState actualStatus = this.testBoard.boardStatus();
        GameConstants.GameState expectedStatus = GameConstants.GameState.DRAW;

        assertEquals(expectedStatus, actualStatus);
        logger.info("✅ shouldReturnCorrectBoardStatusOnDraw passed successfully");
    }

    @Test
    void shouldReturnCorrectBoardStatusWhenGameIsInProgress() {
        this.testBoard.play(testPlayerO, 1);
        this.testBoard.play(testPlayerX, 2);
        this.testBoard.play(testPlayerO, 3);
        this.testBoard.play(testPlayerX, 8);

        GameConstants.GameState actualStatus = this.testBoard.boardStatus();
        GameConstants.GameState expectedStatus = GameConstants.GameState.IN_PROGRESS;

        assertEquals(expectedStatus, actualStatus);
        logger.info("✅ shouldReturnCorrectBoardStatusWhenGameIsInProgress passed successfully");
    }

    @Test
    void shouldResetTheBoardData() {
        this.testBoard.play(testPlayerX, 1);
        this.testBoard.play(testPlayerO, 2);

        this.testBoard.resetBoardData();

        int[][] expectedBoard = new int[3][3];
        assertArrayEquals(expectedBoard, testBoard.getBoard());

        assertEquals(0, this.testBoard.count_X);
        assertEquals(0, this.testBoard.count_O);
        assertNull(this.testBoard.activePlayer);
        assertNull(this.testBoard.winner);
        assertNull(this.testBoard.looser);

        ArrayList<Integer> expectedOccupiedPositions = new ArrayList<>();
        assertEquals(expectedOccupiedPositions, this.testBoard.occupiedPositions);

        ArrayList<Integer> expectedMoves = new ArrayList<>();
        assertEquals(expectedMoves, this.testBoard.moves);

        logger.info("✅ shouldResetTheBoardData passed successfully");
    }

    @Test
    void shouldReturnTheCorrectWinner() {
        simulateWinGame(this.testBoard);

        GameConstants.GameState actualBoardStatus = this.testBoard.boardStatus();

        GameConstants.Player actualWinner = this.testBoard.winner;
        GameConstants.Player expectedWinner = GameConstants.Player.X;

        assertEquals(expectedWinner, actualWinner);

        logger.info("✅ shouldReturnTheCorrectWinner passed successfully");
    }

    @Test
    void shouldReturnTheCorrectLooser() {
        simulateWinGame(this.testBoard);

        this.testBoard.boardStatus();

        GameConstants.Player actualLooser = this.testBoard.looser;
        GameConstants.Player expectedLooser = GameConstants.Player.O;

        assertEquals(expectedLooser, actualLooser);

        logger.info("✅ shouldReturnTheCorrectLooser passed successfully");
    }

    private void simulateWinGame(Board board) {
        board.play(testPlayerX, 1);
        board.play(testPlayerO, 2);
        board.play(testPlayerX, 3);
        board.play(testPlayerO, 4);
        board.play(testPlayerX, 5);
        board.play(testPlayerO, 6);
        board.play(testPlayerX, 9);
    }

}
