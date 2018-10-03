package com.raccoon.takenoko.game;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        assertTrue("Didn't grow bamboo on tile even though it is irrigated.", g.getBoard().get(new Point(0, 1)).getBambooSize() > 0);
    }

    @Test
    public void cantPutBambooOnLake() {
        g.getBoard().get(new Point(0, 0)).increaseBambooSize(1);
        assertEquals("Grew a bamboo on the lake tile.", 0, g.getBoard().get(new Point(0, 0)).getBambooSize());
    }

    @Test
    public void canDecreaseBambooSize() {
        int currentSize = g.getBoard().get(new Point(0, 1)).getBambooSize();
        g.getBoard().get(new Point(0, 1)).decreaseBambooSize();
        assertTrue("Didn't decrease bamboo size even though asked.", g.getBoard().get(new Point(0, 1)).getBambooSize() < currentSize);
    }

    @Test
    public void cantEatWhereThereIsNoBamboo() {
        g.getBoard().get(new Point(0, 0)).decreaseBambooSize();
        assertFalse("Ate a bamboo on a tile where there was no bamboo.", g.getBoard().get(new Point(0, 0)).getBambooSize() < 0);
    }

    @Test
    public void tileNextToLakeIsIrrigated() {
        g.getBoard().set(new Point(0, 2), new BasicTile(Color.PINK));
        g.getBoard().get(new Point(0, 2)).irrigate();
        assertTrue("Tile is not irrigated even though asked.", g.getBoard().get(new Point(0, 1)).isIrrigated());
    }
}
