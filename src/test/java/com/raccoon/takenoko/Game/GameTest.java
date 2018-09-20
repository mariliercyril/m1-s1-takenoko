package com.raccoon.takenoko.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    public void build() {
        game = new Game();
    }

    @Test
    void gameOver() {
        game.start();                   // Play one game
        assertTrue(game.gameOver());    // When the game ends, it should be over
    }

    @Test
    void start() {
        game.start();
        assertNotNull(game.getBoard().get(new Point(0,0)));     // As good as it gets for now
    }

}