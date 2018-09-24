package com.raccoon.takenoko.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void build() {
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

    @Test
    void getWinner() {
        for (int i = 0; i <= 9; i++) {
            game.getPlayers().get(0).play(game);
        }
        assertNotNull(game.getWinner());
        assertSame(game.getWinner(), game.getPlayers().get(0));
    }
}