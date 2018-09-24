package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void build() {
        game = new Game();
        Takeyesntko.VERBOSE = false;
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

    @Test
    void initDeck(){
        assertEquals(27, game.getDeck().size());
    }

    @Test
    void putBackTileTest() {
        Tile test = game.getTile();
        assertFalse(game.getDeck().contains(test));
        game.putBackTile(test);

        assertTrue(game.getDeck().contains(test));
    }

    @Test
    void getTilesTest() {
        ArrayList<Tile> threeTiles = game.getTiles();   // Check that we actually pick three tiles
        assertEquals(3, threeTiles.size());
        while (game.getDeck().size() > 0) {
            game.getTile(); //  Removes three tiles
        }
        game.putBackTile(threeTiles.get(0));
        game.putBackTile(threeTiles.get(1));
        assertEquals(2, game.getDeck().size()); // There should only be two tiles picked since there were only two in the deck
        ArrayList<Tile> twoTiles = game.getTiles();
        assertEquals(2, twoTiles.size());
    }
}