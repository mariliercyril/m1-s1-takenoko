package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.HashBoard;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

import com.raccoon.takenoko.player.Player;

import java.awt.Point;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class allows to test the method {@code checkIfCompleted} of the class {@link PandaObjective}.
 */
public class PandaObjectiveTest {

    // A player (which should be a mock)
	private static Player mockPlayer;

	private HashMap<Color, Integer> stomach;

    private PandaObjective pandaObjective;

	@BeforeAll
	public static void createMockPlayer() {

	    // Creates the mock player
		mockPlayer = mock(Player.class);
	}

	@BeforeEach
	public void initialize() {

		// Creates a stomach and initializes it
		stomach = new HashMap<>();
        stomach.put(Color.GREEN, 0);
        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
	}

    @Test
    @DisplayName("assert true when pandaObjective is completed, 1st case:"
    		+ "the stomach contains 2 bamboo chunks with the expected color")
    public void testCheckIfCompleted_trueFristCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 2);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert true when pandaObjective is completed, 2nd case:"
    		+ "the stomach contains at least 2 bamboo chunks with the expected color")
    public void testCheckIfCompleted_trueSecondCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 3);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted());
    }

	@Test
    @DisplayName("assert true when pandaObjective is completed, 3rd case:"
    		+ "the stomach contains 1 bamboo chunk per color")
	public void testCheckIfCompleted_trueeThirdCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_THREE_COLORS);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);
		stomach.put(Color.YELLOW, 1);
		stomach.put(Color.PINK, 1);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted());
	}

	@Test
    @DisplayName("assert true when pandaObjective is completed, 4th case:"
    		+ "the stomach contains at least 1 bamboo chunk per color")
	public void testCheckIfCompleted_trueeFourthCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_THREE_COLORS);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);
		stomach.put(Color.YELLOW, 2);
		stomach.put(Color.PINK, 1);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted());
	}

    @Test
    @DisplayName("assert false when pandaObjective is not completed, 1st case:"
    		+ "the stomach contains only 1 bamboo chunk with the expected color")
    public void testCheckIfCompleted_falseFirstCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

    	// Fills the stomach with only one bamboo chunk
		stomach.put(Color.GREEN, 1);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when pandaObjective is not completed, 2nd case:"
    		+ "the stomach contains 2 bamboo chunks but only 1 bamboo chunk with the expected color")
    public void testCheckIfCompleted_falseSecondCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);
		stomach.put(Color.YELLOW, 1);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when pandaObjective is not completed, 3rd case:"
    		+ "the stomach contains 2 bamboo chunks but with an unexpected color")
    public void testCheckIfCompleted_falseThirdCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.YELLOW, 2);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted());
    }

    @Test
    @DisplayName("assert false when pandaObjective is not completed, 4th case:"
    		+ "the stomach is empty")
    public void testCheckIfCompleted_falseFourthCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_THREE_COLORS);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted());
    }

	/*
	 * When the CheckIfCompleted method is used with a tile and a board (as parameters)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a tile and a board (as parameters)")
	public void testCheckIfCompletedWithTileAndBoard() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

		// Create the "pond" Tile (initial Tile)
		Tile pondTile = new Tile();
		// Create a first Tile (a first parcel)
		Tile firstTile = new Tile(Color.GREEN);

		// Injects the "pond" Tile in the Board
		Board hashBoard = new HashBoard(pondTile);
		// Places the first Tile
		hashBoard.set(new Point(1, 1), firstTile);

        assertThrows(UnsupportedOperationException.class, () -> pandaObjective.checkIfCompleted(firstTile, hashBoard));
	}

	@Test
	public void objectiveDifferentiationTest() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

    	// Fills the stomach with bamboo chunks
        stomach.put(Color.GREEN, 1);
        stomach.put(Color.YELLOW, 1);
        stomach.put(Color.PINK, 1);

        // So that the mock player returns the stomach
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted(), "Objective validated with 3 different chunks, even though it was supposed to need 2 chunks of the same color");
    }

	@Test
    @DisplayName("assert true when return the expected motif depending on colors of the PandaObjective, 1st case")
	public void testGetMotif_trueFirstCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_GREEN);

		// Create a reference motif
		Map<Color, Integer> motif = new EnumMap<>(Color.class);
		motif.put(Color.GREEN, 2);
		motif.put(Color.YELLOW, 0);
		motif.put(Color.PINK, 0);

		assertEquals(pandaObjective.getMotifForCompleting(), motif);
    }

	@Test
    @DisplayName("assert true when return the expected motif depending on colors of the PandaObjective, 2nd case")
	public void testGetMotif_trueSecondCase() {

		pandaObjective = new PandaObjective(PandaObjective.Motif.ORIGINAL_THREE_COLORS);

		// Create a reference motif
		Map<Color, Integer> motif = new EnumMap<>(Color.class);
		motif.put(Color.GREEN, 1);
		motif.put(Color.YELLOW, 1);
		motif.put(Color.PINK, 1);

		assertEquals(pandaObjective.getMotifForCompleting(), motif);
    }

}
