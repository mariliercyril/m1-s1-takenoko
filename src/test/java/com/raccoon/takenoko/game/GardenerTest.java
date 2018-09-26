package com.raccoon.takenoko.game;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class GardenerTest {

    Board testBoard = new HashBoard(new BasicTile(new Point(0,0)));

    Gardener testGaredner = new Gardener();

    Tile greenTile0 = new BasicTile(Color.GREEN);
    Tile greenTile1 = new BasicTile(Color.GREEN);
    Tile greenTile2 = new BasicTile(Color.GREEN);
    Tile pinkTile0 = new BasicTile(Color.PINK);
    Tile yellowTile0 = new BasicTile(Color.YELLOW);


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

        testGaredner.move(testBoard, new Point(0,1));



        assertTrue("The tile where the gardener is moved didn't grow a bamboo",greenTile0.getBambooSize() == 2);
        assertTrue("The tile adjacent to the tile where the gardener moved didn't grow a bamboo",greenTile1.getBambooSize() == 2);
        assertTrue("The tile adjacent to the gardener grew a bamboo but wasn't of the same colour",pinkTile0.getBambooSize() == 1);
        assertTrue("The tile adjacent to the gardener grew a bamboo but wasn't of the same colour",yellowTile0.getBambooSize() == 1);
        assertTrue("A tile of the right colour but not adjacent to the gardener's one changed its bamboo", greenTile2.getBambooSize() == 1);

    }
}