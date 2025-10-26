package com.tictactoe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIAgentTest {
    private AIAgent ai;
    private Board testBoard;

    @BeforeEach
    void setUp() {
        this.ai = new AIAgent();
        this.testBoard = new Board();
    }

    @AfterEach
    void tearDown() {
        this.ai = null;
        this.testBoard = null;
    }


    @Test
    void play() {

    }

    @Test
    void theStateOfTheBoardIsCopiedSuccessfully() {

    }

    @Test
    void theEvaluationFunctionReturnsTheCorrectBoardState() {

    }

    @Test
    void shouldReturnTheBestMoveGivenTheBoardStatus() {

    }

    @Test
    void shouldReturnAvailablePositionToPlay() {

    }
}