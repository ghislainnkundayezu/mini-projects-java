package com.tictactoe;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AIAgentTest extends BaseTest {
    @Test
    void theStateOfTheBoardIsCopiedSuccessfully() {
        simulateInProgressGame(this.testBoard);

        Board copiedBoard = aiAgent.copyBoard(this.testBoard);

        assertArrayEquals(this.testBoard.getBoard(), copiedBoard.getBoard());
        assertNotSame(this.testBoard.getBoard(), copiedBoard.getBoard());

        assertEquals(this.testBoard, copiedBoard);
        assertNotSame(this.testBoard, copiedBoard);

        logger.info("✅ theStateOfTheBoardIsCopiedSuccessfully passed successfully");
    }

    @Test
    void shouldReturnAvailablePositionToPlay() {
        simulateInProgressGame(this.testBoard);

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(5);
        expected.add(6);
        expected.add(7);
        expected.add(9);

        aiAgent.currentBoard = testBoard;
        ArrayList<Integer> actual = aiAgent.getAvailablePositions();

        assertEquals(expected, actual);

        logger.info("✅ shouldReturnAvailablePositionToPlay passed successfully");
    }

    @Test
    void shouldReturnTrueIfBoardStateIsNotTerminal() {
        this.aiAgent.currentBoard = this.testBoard;
        simulateWinGame(this.testBoard);

        assertTrue(this.aiAgent.terminalState());

        logger.info("✅ shouldReturnTrueIfBoardStateIsNotTerminal passed successfully");
    }

    @Test
    void shouldReturnFalseIfBoardStateIsTerminal() {
        this.aiAgent.currentBoard = this.testBoard;
        simulateInProgressGame(this.testBoard);

        assertFalse(this.aiAgent.terminalState());

        logger.info("✅ shouldReturnFalseIfBoardStateIsTerminal passed successfully");
    }

    @Test
    void shouldReturnTheCorrectScoreOfTheBoardIfTheMaxPlayerWins() {
        simulateWinGame(this.testBoard);

        aiAgent.maxPlayer = this.testPlayerX;
        aiAgent.minPlayer = this.testPlayerO;
        aiAgent.currentBoard = this.testBoard;

        int actualScore = aiAgent.getScore();
        int expectedScore = 1;

        assertEquals(expectedScore, actualScore);

        logger.info("✅ shouldReturnTheCorrectScoreOfTheBoardIfTheMaxPlayerWins passed successfully");
    }

    @Test
    void shouldReturnTheCorrectScoreOfTheBoardIfTheMinPlayerWins() {
        simulateWinGame(this.testBoard);

        aiAgent.maxPlayer = this.testPlayerO;
        aiAgent.minPlayer = this.testPlayerX;
        aiAgent.currentBoard = this.testBoard;

        int actualScore = aiAgent.getScore();
        int expectedScore = -1;

        assertEquals(expectedScore, actualScore);

        logger.info("✅ shouldReturnTheCorrectScoreOfTheBoardIfTheMinPlayerWins passed successfully");
    }

    @Test
    void shouldReturnTheCorrectScoreOfTheBoardIfTheOnGameDraw() {
        simulateDrawGame(this.testBoard);

        aiAgent.maxPlayer = this.testPlayerO;
        aiAgent.minPlayer = this.testPlayerX;
        aiAgent.currentBoard = this.testBoard;

        int actualScore = aiAgent.getScore();
        int expectedScore = 0;

        assertEquals(expectedScore, actualScore);

        logger.info("✅ shouldReturnTheCorrectScoreOfTheBoardIfTheOnGameDraw passed successfully");
    }

    @Test
    void shouldReturnTheBestScoreOfAGivenMove() {
        this.testBoard.play(testPlayerX, 5);
        this.testBoard.play(testPlayerO, 1);
        this.testBoard.play(testPlayerX, 2);

        aiAgent.maxPlayer = this.testPlayerO;
        aiAgent.minPlayer = this.testPlayerX;
        aiAgent.currentBoard = this.testBoard;

        int actualBestScore = aiAgent.minimaxScore();
        int expectedBestScore = 0;

        assertEquals(expectedBestScore, actualBestScore);

        logger.info("✅ shouldReturnTheBestScoreOfAGivenMove passed successfully");
    }

    @Test
    void shouldReturnTheBestMove() {
        this.testBoard.play(testPlayerX, 5);
        this.testBoard.play(testPlayerO, 1);
        this.testBoard.play(testPlayerX, 2);

        int actualBestMove = aiAgent.play(this.testBoard, this.testPlayerO, this.testPlayerX);
        int expectedBestMove = 8;

        assertEquals(expectedBestMove, actualBestMove);

        logger.info("✅ shouldReturnTheBestMove passed successfully");
    }

    @Test
    void evaluationFunctionShouldThrowAnErrorWhenTheBoardIsNotInATerminalState() {
        this.testBoard.play(testPlayerX, 5);
        this.testBoard.play(testPlayerO, 1);
        this.testBoard.play(testPlayerX, 2);

        aiAgent.maxPlayer = this.testPlayerO;
        aiAgent.minPlayer = this.testPlayerX;
        aiAgent.currentBoard = this.testBoard;

        Exception exception = assertThrows(
                IllegalStateException.class,
                () -> aiAgent.getScore()
        );

        String expectedErrorMessage = "Cannot evaluate: the game is still in progress.";
        assertTrue(expectedErrorMessage.equalsIgnoreCase(exception.getMessage()));

        logger.info("✅ evaluationFunctionShouldThrowAnErrorWhenTheBoardIsNotInATerminalState passed successfully");
    }
}