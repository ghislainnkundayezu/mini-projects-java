package com.tictactoe;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static final Logger logger = Logger.getLogger(BoardTest.class.getName());

    private Board board;
    private int testValidPosition;
    private int testInvalidPosition;
    private GameConstants.Player testPlayerX;
    private GameConstants.Player testPlayerO;

    @BeforeEach
    void setUp() {
        // runs BEFORE each test method
        board = new Board();
        testValidPosition = 5;
        testInvalidPosition = 11;
        testPlayerX = GameConstants.Player.X;
        testPlayerO = GameConstants.Player.O;
    }

    @AfterEach
    void tearDown() {
        // runs AFTER each test method
        board.resetBoardData();
        board = null;
        testValidPosition = -1;
        testInvalidPosition = -1;
        testPlayerX = null;
        testPlayerO = null;
    }

    @Test
    void boardStartsEmpty() {
        int[][] result = this.board.getBoard();
        int[][] expected = new int[3][3];

        assertArrayEquals(result, expected);
        logger.info("✅ testBoardStartsEmpty passed successfully");
    }

    @Test
    void updatesBoardDataSuccessfully() {
        board.updateBoard(testPlayerX, testValidPosition);
        int[][] result = board.getBoard();

        int[][] expected = new int[3][3];
        int row = (testValidPosition - 1) / 3;
        int col = (testValidPosition - 1) % 3;
        expected[row][col] = testPlayerX.getValue();

        // Test if the player was place in the correct position.
        assertArrayEquals(result, expected);

        // Test if the position was added to the occupied positions array.
        assertTrue(board.occupiedPositions.contains(testValidPosition));

        // Test if the player was added to the moves array.
        assertTrue(board.moves.contains(testPlayerX.getValue()));

        // Test if the count the corresponding player increased.
        assertEquals(1, board.count_X);
        assertEquals(0, board.count_O);

        logger.info("✅ updatesBoardDataSuccessfully passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfPositionIsInvalid() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.board.play(testPlayerX, testInvalidPosition)
        );

        String expectedErrorMessage = "Invalid Position";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfPositionIsInvalid passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfPositionIsOccupied() {
        this.board.play(testPlayerX, testValidPosition);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.board.play(testPlayerO, testValidPosition)
        );

        String expectedErrorMessage = "Position is already occupied";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfPositionIsOccupied passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfTheSamePlayerPlaysTwice() {
        this.board.play(testPlayerO, testValidPosition);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.board.play(testPlayerO, 6)
        );

        String expectedErrorMessage = "This Player cannot twice before their opponent plays.";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfTheSamePlayerPlaysTwice passed successfully");
    }

    @Test
    void shouldReturnCorrectBoardStatusOnWin() {

    }

    @Test
    void shouldReturnCorrectBoardStatusOnDraw() {

    }

    @Test
    void shouldReturnCorrectBoardStatusOnNothing() {

    }

}
