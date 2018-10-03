package com.raccoon.takenoko.game;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class BasicTileTest {
    private Game g;

    @Before
    public void setup() {
        g = new Game();
        g.getBoard().set(new Point(0, 1), new BasicTile(Color.GREEN));
        g.getGardener().move(g.getBoard(), new Point(0, 1));
    }

    @Test
    public void canIncreaseBambooSize() {
        assertTrue("Didn't grow bamboo on tile even though it is irrigated!", g.getBoard().get(new Point(0, 1)).getBambooSize() > 0);
    }

    @Test
    public void cantPutBambooOnLake() {
        g.getBoard().get(new Point(0, 0)).increaseBambooSize(1);
        assertEquals("Grew a bamboo on the lake tile.", 0, g.getBoard().get(new Point(0, 0)).getBambooSize());
    }
}
