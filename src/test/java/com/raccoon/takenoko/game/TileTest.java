package com.raccoon.takenoko.game;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.UnitVector;
import com.raccoon.takenoko.tool.Vector;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TileTest {
    private Game g;

    Tile origin;
    Tile t1;

    @Before
    public void setup() {
        g = new Game();
        g.getBoard().set(new Point(0, 1), new Tile(com.raccoon.takenoko.game.tiles.Color.GREEN));

        // put down some tiles
        g.getBoard().set(new Point(1, 0), new Tile(com.raccoon.takenoko.game.tiles.Color.PINK));
        g.getBoard().set(new Point(0, -1), new Tile(Color.GREEN));

        // keep them somewhere (for lisibility)
        origin = g.getBoard().get(new Point(0, 0));
        t1 = g.getBoard().get(new Point(1, 0));

        // irrigate them where possible
        t1.irrigate(UnitVector.M);
        t1.irrigate(UnitVector.N);

        g.getGardener().move(g.getBoard(), new Point(0, 1));
    }

    @Test
    public void canIncreaseBambooSize() {
        assertTrue("Didn't grow bamboo on tile even though it is irrigated.", t1.getBambooSize() > 0);
    }

    @Test
    public void cantPutBambooOnLake() {
        origin.increaseBambooSize(1);
        assertEquals("Grew a bamboo on the lake tile.", 0, origin.getBambooSize());
    }

    @Test
    public void canDecreaseBambooSize() {
        int currentSize = t1.getBambooSize();
        t1.decreaseBambooSize();
        assertTrue("Didn't decrease bamboo size even though asked.", t1.getBambooSize() < currentSize);
    }

    @Test
    public void cantEatWhereThereIsNoBamboo() {
        origin.decreaseBambooSize();
        assertFalse("Ate a bamboo on a tile where there was no bamboo.", origin.getBambooSize() < 0);
    }

    @Test
    public void tileNextToLakeIsIrrigated() {
        assertTrue("Tile is not irrigated even though asked.", t1.isIrrigated());
        assertTrue("Tile has not been irrigated in the right direction", t1.getIrrigatedTowards().contains(new Vector(0, -1)));
    }

}
