package com.raccoon.takenoko.Player;

import com.raccoon.takenoko.Game.Game;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomBotTest {
    private Player p;
    private Game g;

    @Before
    public void build() {
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
        assertTrue(g.getBoard().getNeighbours(new Point(0,0)).size() > 0);
    }
}
