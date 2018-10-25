package com.raccoon.takenoko.game;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.IrrigationState;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.UnitVector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HashBoardTest {

    @Mock
    Tile tile0;
    @Mock
    Tile tile1;
    @Mock
    Tile tile2;

    private Board b;

    private Game g;

    private Tile t1;
    private Tile t2;
    private Tile t3;

    private Tile t4;
    private Tile t5;
    private Tile t6;
    private Tile t7;


    @Before
    public void setUp() {
        g = new Game();
        b = g.getBoard();
        b.set(new Point(0, 1), new Tile(Color.GREEN));

        // put down some tiles
        b.set(new Point(1, 0), new Tile(Color.PINK));
        b.set(new Point(0, -1), new Tile(Color.GREEN));
        b.set(new Point(1, -1), new Tile(Color.YELLOW));

        b.set(new Point(1, 1), new Tile(Color.YELLOW));
        b.set(new Point(0, 1), new Tile(Color.YELLOW));
        b.set(new Point(2, 1), new Tile(Color.YELLOW));
        b.set(new Point(2, 2), new Tile(Color.YELLOW));

        // keep them somewhere (for lisibility)
        Tile origin = b.get(new Point(0, 0));

        t1 = b.get(new Point(1, 0));
        t2 = b.get(new Point(0, -1));
        t3 = b.get(new Point(1, -1));

        t4 = b.get(new Point(1, 1));
        t5 = b.get(new Point(0, 1));
        t6 = b.get(new Point(2, 1));
        t7 = b.get(new Point(2, 2));

        // irrigate them where possible
        b.irrigate(t1.getPosition(), UnitVector.M);
        b.irrigate(t1.getPosition(), UnitVector.N);
        b.irrigate(t1.getPosition(), UnitVector.K);

        when(tile1.getIrrigationState(any())).thenReturn(IrrigationState.IRRIGABLE);
        when(tile2.getIrrigationState(any())).thenReturn(IrrigationState.IRRIGABLE);
    }

    @Test
    public void getSet() {

        HashBoard board = new HashBoard(tile0);         // Creation of a new HashBoard
        verify(tile0, atLeastOnce()).setPosition(any(Point.class));    // Check that the tile coordinates has been set

        assertEquals(board.get(new Point(0, 0)), tile0);     // Check that the get returns the correct tile

        board.set(new Point(0, 1), tile1);

        verify(tile1).setPosition(eq(new Point(0, 1)));  // Check that the tile coordinates has been set here again


        assertEquals(board.get(new Point(0, 1)), tile1);

    }

    @Test
    public void TestGetNeighbours() {

        HashBoard board = new HashBoard(tile0);

        board.set(new Point(0, 1), tile1);
        board.set(new Point(1, 0), tile2);

        assertTrue(board.getAvailablePositions().contains(new Point(1, 1)));
        assertFalse(board.getAvailablePositions().contains(new Point(1, 2)));
        assertFalse(board.getAvailablePositions().contains(new Point(0, 1)));

    }

    @Test
    public void irrigationTest() {
        HashBoard board = new HashBoard(tile0);
        Tile test_wet = new Tile(com.raccoon.takenoko.game.tiles.Color.YELLOW);
        board.set(new Point(0, 1), test_wet);
        board.set(new Point(1, 1), tile1);   // We need a tile here to put another one in (1, 2) for the next test
        assertTrue("The tile should be irrigated because it's adjacent to the pond", test_wet.isIrrigated());

        Tile test_dry = new Tile();
        board.set(new Point(1, 2), test_dry);
        assertFalse("This tile shouldn't be irrigated because none of its neighbors have an adjacent irrigation", test_dry.isIrrigated());
    }

    @Test
    public void irrigateTest() {
        assertTrue("Irrigation can't be put between to correct tiles", b.irrigate(t1.getPosition(), UnitVector.J));
        assertFalse("Irrigation put on a tile with no other tile adjacent", b.irrigate(t3.getPosition(), UnitVector.K.opposite()));

    }

    //@Ignore
    @Test
    public void accessiblePositionsTest() {
        Board board = new HashBoard(tile0);

        Point start = new Point(0, 0);

        board.set(new Point(0, -1), new Tile(Color.YELLOW));
        board.set(new Point(1, 0), new Tile(Color.YELLOW));
        board.set(new Point(1, -1), new Tile(Color.YELLOW));

        assertTrue(board.getAccessiblePositions(start).contains(new Point(0, -1)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(1, 0)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(1, -1)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(-1, -2)));

        board.set(new Point(-1, -1), new Tile(Color.YELLOW));
        board.set(new Point(-1, -2), new Tile(Color.YELLOW));
        board.set(new Point(-2, -2), new Tile(Color.YELLOW));
        board.set(new Point(0, -2), new Tile(Color.YELLOW));
        board.set(new Point(2, 0), new Tile(Color.YELLOW));

        assertTrue(board.getAccessiblePositions(start).contains(new Point(-2, -2)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(2, 0)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(0, -2)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(-1, -2)));
    }

    @Test
    public void irrigatedTowardsSomething() {
        assertTrue("Tile next to last irrigated tile, and in the right direction, is not irrigated", t3.isIrrigated());
        // K is the unit Vector (0, 1)
        assertEquals("Next tile irrigated is irrigated in the wrong direction.", IrrigationState.IRRIGATED, t3.getIrrigationState(UnitVector.K));
    }

    @Test
    public void isIrrigable() {
        // test case with current setup
        assertFalse("Should not be irrigable for there is no water path to it", t7.isIrrigable());
        assertTrue("Should be irrigable as it in sext to pond.", t4.isIrrigable());

        // add an irrigation and check consequences
        assertTrue("If previous test passed, should be irrigated.", b.irrigate(t4.getPosition(), UnitVector.I));
        assertTrue("Just set a path to it, should be irrigable.", t6.isIrrigable());
        assertTrue("Just set a path to it, should be irrigable by now.", t7.isIrrigable());
        assertEquals("should be irrigable in this direction once a tile is put down", IrrigationState.TO_BE_IRRIGABLE, t1.getIrrigationState(UnitVector.I));

        // set a tile at an irrigable place and check if I can put down an irrigation between them.
        b.set(new Point(2, 0), new Tile(Color.PINK));
        assertEquals("Just put down a tile at the right place, this place should be irrigable.", IrrigationState.IRRIGABLE, t1.getIrrigationState(UnitVector.I));
        assertEquals("Just put down a tile at the right place, the new tile should be irrigable.", IrrigationState.IRRIGABLE, b.get(new Point(2, 0)).getIrrigationState(UnitVector.I.opposite()));

        // irrigate an entire tile and check consequences on said tile
        b.irrigate(t1.getPosition(), UnitVector.I);
        b.irrigate(t1.getPosition(), UnitVector.J);
        assertFalse("Tile should be entirely irrigated, can't set an irrigation there anymore.", t1.isIrrigable());
    }

}