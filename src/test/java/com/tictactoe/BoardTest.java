package com.tictactoe;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest extends BaseTest {

    @Test
    void boardStartsEmpty() {
        int[][] result = this.testBoard.getBoard();
        int[][] expected = new int[3][3];

        assertArrayEquals(result, expected);
        logger.info("✅ testBoardStartsEmpty passed successfully");
    }

    @Test
    void updatesBoardDataSuccessfully() {
        testBoard.updateBoard(testPlayerX, 5);
        int[][] result = testBoard.getBoard();

        int[][] expected = new int[3][3];
        int row = (5 - 1) / 3;
        int col = (5 - 1) % 3;
        expected[row][col] = testPlayerX.getValue();

        // Test if the player was placed in the correct position.
        assertArrayEquals(result, expected);

        // Test if the position was added to the occupied positions array.
        assertTrue(testBoard.occupiedPositions.contains(5));

        // Test if the player was added to the moves array.
        assertTrue(testBoard.moves.contains(testPlayerX.getValue()));

        // Test if the count the corresponding player increased.
        assertEquals(1, testBoard.count_X);
        assertEquals(0, testBoard.count_O);

        logger.info("✅ updatesBoardDataSuccessfully passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfPositionIsInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.testBoard.play(testPlayerX, 11));

        String expectedErrorMessage = "Invalid Position";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfPositionIsInvalid passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfPositionIsOccupied() {
        this.testBoard.play(testPlayerX, 5);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.testBoard.play(testPlayerO, 5));

        String expectedErrorMessage = "Position is already occupied";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ shouldThrowAnErrorIfPositionIsOccupied passed successfully");
    }

    @Test
    void shouldThrowAnErrorIfTheSamePlayerPlaysTwice() {
        this.testBoard.play(testPlayerO, 5);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> this.testBoard.play(testPlayerO, 6));

        String expectedErrorMessage = "This Player cannot play twice before their opponent plays.";
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
        simulateDrawGame(this.testBoard);

        GameConstants.GameState actualStatus = this.testBoard.boardStatus();
        GameConstants.GameState expectedStatus = GameConstants.GameState.DRAW;

        assertEquals(expectedStatus, actualStatus);
        logger.info("✅ shouldReturnCorrectBoardStatusOnDraw passed successfully");
    }

    @Test
    void shouldReturnCorrectBoardStatusWhenGameIsInProgress() {
        simulateInProgressGame(this.testBoard);

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

        this.testBoard.boardStatus();

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

    @Test
    void shouldReturnTheCorrectActivePlayer() {
        simulateWinGame(this.testBoard);

        GameConstants.Player expectedActivePlayer = GameConstants.Player.X;
        assertEquals(expectedActivePlayer, this.testBoard.activePlayer);
    }

    @Test
    void shouldReturnTrueWhenBoardIsEmpty() {
        assertTrue(this.testBoard.boardIsEmpty());
    }

    @Test
    void shouldReturnFalseWhenBoardIsNotEmpty() {
        simulateInProgressGame(this.testBoard);
        assertFalse(testBoard.boardIsEmpty());
    }

    @Test
    void shouldUndoTheRecentMove() {
        simulateWinGame(this.testBoard);
        this.testBoard.boardStatus();

        this.testBoard.undoRecentMove();
        // test if updated player count
        int expectedCount = 3;
        assertEquals(expectedCount, this.testBoard.count_X);

        // test if updated active player
        GameConstants.Player expectedActivePlayer = GameConstants.Player.O;
        assertEquals(expectedActivePlayer, this.testBoard.activePlayer);

        // test if updated board
        int expectedValue = 0;
        int actualValue = this.testBoard.getBoard()[2][2];
        assertEquals(expectedValue, actualValue);

        // test if updated winner and looser attributes
        assertNull(this.testBoard.winner);
        assertNull(this.testBoard.looser);

        // test if removed the player from moves array
        int expectedLastPlayer = GameConstants.Player.O.getValue();
        int actualLastPlayer = this.testBoard.moves.getLast();

        assertEquals(expectedLastPlayer, actualLastPlayer);

        // test if removed the position from occupied positions array
        int expectedLastPosition = 6;
        int actualLastPosition = this.testBoard.occupiedPositions.getLast();

        assertEquals(expectedLastPosition, actualLastPosition);
    }
}
