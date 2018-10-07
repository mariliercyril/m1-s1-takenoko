package com.raccoon.takenoko.game.objective.panda;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import java.util.HashMap;

import com.raccoon.takenoko.game.BasicTile;
import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.HashBoard;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.player.Player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class allows to test the method {@code checkIfCompleted} of the class {@link TwoBambooChunksPandaObjective}.
 */
public class TwoBambooChunksPandaObjectiveTest {

    // A player (which should be a mock) 
	private static Player mockPlayer;

	private HashMap<Color, Integer> stomach;

    private TwoBambooChunksPandaObjective twoBambooChunksPandaObjective;

	@BeforeAll
	public static void createsMockPlayer() {

	    // Creates the mock player
		mockPlayer = mock(Player.class);
	}

	@BeforeEach
	public void initialize() {

		// Creates a stomach
		stomach = new HashMap<>();

		twoBambooChunksPandaObjective = new TwoBambooChunksPandaObjective(Color.GREEN);
	}

    @Test
    @DisplayName("assert true when twoBambooChunksPandaObjective is completed, 1st case: the stomach contains 2 bamboo chunks with the expected color")
    public void testCheckIfCompleted_trueFristCase() {

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 2);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        twoBambooChunksPandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(twoBambooChunksPandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert true when twoBambooChunksPandaObjective is completed, 2nd case: the stomach contains at least 2 bamboo chunks with the expected color")
    public void testCheckIfCompleted_trueSecondCase() {

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 3);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        twoBambooChunksPandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(twoBambooChunksPandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when twoBambooChunksPandaObjective is not completed, 1st case: the stomach contains only 1 bamboo chunk with the expected color")
    public void testCheckIfCompleted_falseFirstCase() {

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        twoBambooChunksPandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(twoBambooChunksPandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when twoBambooChunksPandaObjective is not completed, 2nd case: the stomach contains 2 bamboo chunks but only 1 bamboo chunk with the expected color")
    public void testCheckIfCompleted_falseSecondCase() {

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);
		stomach.put(Color.YELLOW, 1);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        twoBambooChunksPandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(twoBambooChunksPandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when twoBambooChunksPandaObjective is not completed, 3rd case: the stomach contains 2 bamboo chunks but with an unexpected color")
    public void testCheckIfCompleted_falseThirdCase() {

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.YELLOW, 2);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        twoBambooChunksPandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(twoBambooChunksPandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when twoBambooChunksPandaObjective is not completed, 4th case: the stomach is empty")
    public void testCheckIfCompleted_falseFourthCase() {

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        twoBambooChunksPandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(twoBambooChunksPandaObjective.isCompleted());
    }

	/*
	 * When the CheckIfCompleted method is used with a tile and a board (as paramters)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a tile and a board (as paramters)")
	public void testCheckIfCompletedWithTileAndBoard() {

		// Create the "pond" Tile (initial Tile)
		Tile pondTile = new BasicTile();
		// Create a first Tile (a first parcel)
		Tile firstTile = new BasicTile(Color.GREEN);

		// Injects the "pond" Tile in the Board
		Board hashBoard = new HashBoard(pondTile);
		// Places the first Tile
		hashBoard.set(new Point(1, 1), firstTile);

        assertThrows(UnsupportedOperationException.class, () -> twoBambooChunksPandaObjective.checkIfCompleted(firstTile, hashBoard));
	}

}
