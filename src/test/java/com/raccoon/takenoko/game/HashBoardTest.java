package com.raccoon.takenoko.game;

import com.raccoon.takenoko.tool.UnitVector;
import com.raccoon.takenoko.tool.Vector;
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

@RunWith(MockitoJUnitRunner.class)
public class HashBoardTest {

    @Mock
    BasicTile tile0;
    @Mock
    BasicTile tile1;
    @Mock
    BasicTile tile2;

    private Game g;

    private Tile origin;
    private Tile t1;
    private Tile t2;
    private Tile t3;


    @Before
    public void setUp() {
        g = new Game();
        g.getBoard().set(new Point(0, 1), new BasicTile(Color.GREEN));

        // put down some tiles
        g.getBoard().set(new Point(1, 0), new BasicTile(Color.PINK));
        g.getBoard().set(new Point(0, -1), new BasicTile(Color.GREEN));
        g.getBoard().set(new Point(1, -1), new BasicTile(Color.YELLOW));

        // keep them somewhere (for lisibility)
        origin = g.getBoard().get(new Point(0, 0));
        t1 = g.getBoard().get(new Point(1, 0));
        t2 = g.getBoard().get(new Point(0, -1));
        t3 = g.getBoard().get(new Point(1, -1));

        // irrigate them where possible
        g.getBoard().irrigate(t1.getPosition(), new Vector(-1, -1));
        g.getBoard().irrigate(t1.getPosition(), new Vector(0, -1));
    }

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

    @Test
    public void irrigateTest() {
        Board board = g.getBoard();

        assertTrue("Irrigation can't be put between to correct tiles", board.irrigate(new Point(0,-1), UnitVector.K.getVector ()));
        assertFalse("Irrigation put on a tile with no other tile adjacent", board.irrigate(new Point(1, -1), UnitVector.K.getVector ().getOpposite()));

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
        assertFalse(board.getAccessiblePositions(start).contains(new Point(-1,-2)));

        board.set(new Point(-1, -1), new BasicTile());
        board.set(new Point(-1, -2), new BasicTile());
        board.set(new Point(-2, -2), new BasicTile());
        board.set(new Point(0, -2), new BasicTile());
        board.set(new Point(2, 0), new BasicTile());

        assertTrue(board.getAccessiblePositions(start).contains(new Point(-2,-2)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(2, 0)));
        assertTrue(board.getAccessiblePositions(start).contains(new Point(0, -2)));
        assertFalse(board.getAccessiblePositions(start).contains(new Point(-1, -2)));
    }

    @Test
    public void irrigatedTowardsSomething() {
        assertTrue("Tile next to last irrigated tile, and in the right direction, is not irrigated", t3.isIrrigated());
        assertTrue("Next tile irrigated is irrigated in the wrong direction.", t3.getIrrigatedTowards().contains(new Vector(0, 1)));
    }
}