package com.raccoon.takenoko.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
class HashBoardTest {

    @Mock Tile tile0;
    @Mock Tile tile1;
    @Mock Tile tile2;

    /*
    @BeforeEach
    void setUp() {

    }*/

    @Test
    void getSet() {

        HashBoard board = new HashBoard(tile0);         // Creation of a new HashBoard
        verify(tile0).setPosition(any(Point.class));    // Check that the tile coordinates has been set

        assertEquals(board.get(new Point(0,0)), tile0);     // Check that the get returns the correct tile

        board.set(new Point(0,1), tile1);
        //verify(tile1).setPosition(eq);    // Check that the tile coordinates has been set here again


    }
    @Test
    void getAvailablePositions() {
    }

    @Test
    void getNeighbours() {
    }
}