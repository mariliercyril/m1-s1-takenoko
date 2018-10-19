package com.raccoon.takenoko.game;

import com.raccoon.takenoko.tool.UnitVector;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class PawnTest {

    private Board testBoard = new HashBoard(new Tile());

    private Gardener testGaredner = new Gardener();
    private Panda testPanda = new Panda();

    private Tile greenTile0 = new Tile(Color.GREEN);
    private Tile greenTile1 = new Tile(Color.GREEN);
    private Tile greenTile2 = new Tile(Color.GREEN);
    private Tile pinkTile0 = new Tile(Color.PINK);
    private Tile yellowTile0 = new Tile(Color.YELLOW);
    private Tile greenTile3 = new Tile(Color.GREEN);


    @Test
    public void move() {

        /*
        We add 5 tiles to create a board
         */
        testBoard.set(new Point(0,1), greenTile0);
        testBoard.set(new Point(1,1), pinkTile0);
        testBoard.set(new Point(1,2), greenTile1);
        testBoard.set(new Point(0,-1), greenTile2);
        testBoard.set(new Point(0,2), yellowTile0);
        testBoard.set(new Point(1,0), greenTile3);

        testBoard.irrigate(new Point(1, 1), UnitVector.L.getVector());
        testBoard.irrigate(new Point(1, 1), UnitVector.K.getVector());

        assertEquals(1, testBoard.get(new Point(1, 2)).getBambooSize());

        testGaredner.move(testBoard, new Point(0,1));
        assertEquals("The tile adjacent to the tile where the gardener moved didn't grow a bamboo", 2, greenTile1.getBambooSize());

        testPanda.move(testBoard, new Point(1,2));  // For the purpose of this test, it doesn't matter whether the panda is actually allowed to the tiles, we are only testing the effects of the panda arriving
        // The following tests also make sure that the surrounding tiles weren't affected by the panda
        assertTrue("The tile where the panda landed didn't have one piece of bamboo eaten",greenTile1.getBambooSize() == 1);
        testPanda.move(testBoard, new Point(1,0));
        assertTrue("The tile where the panda landed had a piece of bamboo eaten even though there wasn't any bamboo to eat", greenTile3.getBambooSize() == 0);
        assertTrue("The tile where the gardener is moved didn't grow a bamboo",greenTile0.getBambooSize() == 2);
        assertTrue("The tile adjacent to the gardener grew a bamboo but wasn't of the same colour",pinkTile0.getBambooSize() == 1);
        assertTrue("The tile adjacent to the gardener grew a bamboo but wasn't of the same colour",yellowTile0.getBambooSize() == 0);
        assertTrue("A tile of the right colour but not adjacent to the gardener's one changed its bamboo", greenTile2.getBambooSize() == 1);

    }
}