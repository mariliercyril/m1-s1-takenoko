package com.raccoon.takenoko.game;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
}