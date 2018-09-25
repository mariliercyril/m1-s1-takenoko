package com.raccoon.takenoko.game;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HashBoardTest {

    @Mock
    BasicTile tile0;
    @Mock
    BasicTile tile1;
    @Mock
    BasicTile tile2;

    /*
    @BeforeEach
    void setUp() {

    }*/

    @Test
    public void getSet() {

        HashBoard board = new HashBoard(tile0);         // Creation of a new HashBoard
        verify(tile0, atLeastOnce()).setPosition(any(Point.class));    // Check that the tile coordinates has been set

        assertEquals(board.get(new Point(0,0)), tile0);     // Check that the get returns the correct tile

        board.set(new Point(0,1), tile1);

        verify(tile1).setPosition(eq(new Point(0,1)));  // Check that the tile coordinates has been set here again

        assertEquals(board.get(new Point(0,1)), tile1);

    }
    @Test
    public void TestGetNeighbours() {

        HashBoard board = new HashBoard(tile0);

        board.set(new Point(0,1), tile1);
        board.set(new Point(1,0), tile2);

        assertTrue(board.getAvailablePositions().contains(new Point(1,1)));
        assertFalse(board.getAvailablePositions().contains(new Point(1,2)));
        assertFalse(board.getAvailablePositions().contains(new Point(0,1)));

    }

    @Test
    public void irrigationTest() {
        HashBoard board = new HashBoard(tile0);
        Tile test_wet = new BasicTile();
        board.set(new Point(0,1), test_wet);
        board.set(new Point(1,1), tile1);   // We need a tile here to put another one in (1, 2) for the next test
        assertTrue("The tile should be irrigated because it's adjacent to the pond", test_wet.isIrrigated());
        /* todo : uncomment this test once the tiles can not be irrigated
        Tile test_dry = new BasicTile();
        board.set(new Point(1,2), test_dry);
        assertFalse("This tile shouldn't be irrigated because none of its neighbors have an adjacent irrigation", test_dry.isIrrigated());*/
    }
    //@Ignore
    @Test
    public void accessiblePositionsTest() {
        Board board = new HashBoard(tile0);

        Point start = new Point(0,0);

        board.set(new Point(0, -1), new BasicTile());
        board.set(new Point(1,0), new BasicTile());
        board.set(new Point(1,-1), new BasicTile());

        assertTrue(board.getAccessiblePositions(start).contains(new Point(0, -1)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(1,0)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(1,-1)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(1,-2)));
    }
}