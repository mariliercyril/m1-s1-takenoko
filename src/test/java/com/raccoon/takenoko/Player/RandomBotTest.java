package com.raccoon.takenoko.Player;

import com.raccoon.takenoko.Game.Game;

import com.raccoon.takenoko.Takeyesntko;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomBotTest {
    private Player p;
    private Game g;

    @Before
    public void build() {
        Takeyesntko.VERBOSE = false;

        g = new Game();
        p = new RandomBot();
    }

    @Test
    public void testCreation() {
        assertEquals(p.getScore(), 0);
    }

    @Test
    public void testPlayIncidenceOnScore() {
        p.play(g);
        assertEquals(p.getScore(), 1);
    }

    @Test
    public void testPlayIncidenceOnBoard() {
        p.play(g);
        // we test that the starting tile has at least one neighbour
        assertTrue(g.getBoard().getNeighbours(new Point(0, 0)).size() > 0);
    }
}
