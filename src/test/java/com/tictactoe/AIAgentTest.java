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
    }

    @Test
    void theEvaluationFunctionReturnsTheCorrectBoardState() {

    }

    @Test
    void shouldReturnTheBestMoveGivenTheBoardStatus() {

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

    }

    @Test
    void shouldReturnTrueIfBoardStateIsNotTerminal(){
        this.aiAgent.currentBoard = this.testBoard;
        simulateWinGame(this.testBoard);

        assertTrue(this.aiAgent.terminalState());
    }

    @Test
    void shouldReturnFalseIfBoardStateIsTerminal(){
        this.aiAgent.currentBoard = this.testBoard;
        simulateInProgressGame(this.testBoard);

        assertFalse(this.aiAgent.terminalState());
    }
}