package com.tictactoe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

public class BaseTest {
    protected static final Logger logger =
            Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    protected Board testBoard;
    protected AIAgent aiAgent;
    protected GameConstants.Player testPlayerX;
    protected GameConstants.Player testPlayerO;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        // runs BEFORE each test method
        testBoard = new Board();
        aiAgent = new AIAgent();
        testPlayerX = GameConstants.Player.X;
        testPlayerO = GameConstants.Player.O;
        System.out.println("Starting test for " + testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        // runs AFTER each test method
        testBoard.resetBoardData();
        // TODO Reset the data for the AI Agent.
        testBoard = null;
        testPlayerX = null;
        testPlayerO = null;
        System.out.println("Ending test for " + testInfo.getDisplayName());
    }

    protected void simulateWinGame(Board board) {
        board.play(testPlayerX, 1);
        board.play(testPlayerO, 2);
        board.play(testPlayerX, 3);
        board.play(testPlayerO, 4);
        board.play(testPlayerX, 5);
        board.play(testPlayerO, 6);
        board.play(testPlayerX, 9);
    }

    protected void simulateDrawGame(Board board) {
        this.testBoard.play(testPlayerX, 1);
        this.testBoard.play(testPlayerO, 5);
        this.testBoard.play(testPlayerX, 4);
        this.testBoard.play(testPlayerO, 7);
        this.testBoard.play(testPlayerX, 3);
        this.testBoard.play(testPlayerO, 2);
        this.testBoard.play(testPlayerX, 8);
        this.testBoard.play(testPlayerO, 6);
        this.testBoard.play(testPlayerX, 9);
    }

    protected void simulateInProgressGame(Board board) {
        this.testBoard.play(testPlayerO, 1);
        this.testBoard.play(testPlayerX, 2);
        this.testBoard.play(testPlayerO, 3);
        this.testBoard.play(testPlayerX, 8);
    }
}

