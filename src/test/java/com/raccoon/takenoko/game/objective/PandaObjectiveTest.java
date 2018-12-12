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
class PandaObjectiveTest {

    // A player (which should be a mock)
	private static Player mockPlayer;

	private HashMap<Color, Integer> stomach;

    private PandaObjective pandaObjective;

	@BeforeAll
	static void createMockPlayer() {

	    // Creates the mock player
		mockPlayer = mock(Player.class);
	}

	@BeforeEach
	void initialize() {

		// Creates a stomach and initializes it
		stomach = new HashMap<>();
        stomach.put(Color.GREEN, 0);
        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
	}

    @Test
    @DisplayName("Check that a single colour objective is completed with a compliant stomach")
    void testCheckIfCompletedSingleColour() {

		pandaObjective = new PandaObjective(2,0,0,0);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 2);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted(), "Objective not completed although we had just the right amount of bamboos in the stomach");

		// Reinitialisation of the completion flag
        pandaObjective.isCompleted = false;

        // New stomach configuration, also valid
        stomach.put(Color.GREEN, 3);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted(), "Objective not completed although we had more bamboo than needed in the stomach");
    }

	@Test
    @DisplayName("Check that a three colours objective is completed with a compliant stomach")
	void testCheckIfCompletedThreeColours() {

        // Stub of the mocked player
        when(mockPlayer.getStomach()).thenReturn(stomach);

		pandaObjective = new PandaObjective(1,1,1,0);

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);
		stomach.put(Color.YELLOW, 1);
		stomach.put(Color.PINK, 1);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted(), "Three colours objective not completed with one bamboo of each type in the stomach");

        // Re-initialisation of the completion flag
        pandaObjective.isCompleted = false;

        // We'll try with more bamboos than needed this time
		stomach.put(Color.YELLOW, 2);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertTrue(pandaObjective.isCompleted(), "Three colours objective not completed with more bamboo than needed in the stomach");
	}

    @Test
    @DisplayName("Check we don't complete objectives when the requirement are not met")
    void testCheckIfCompletedntSingleColor() {

	    // Stub of the mock player
        when(mockPlayer.getStomach()).thenReturn(stomach);

		pandaObjective = new PandaObjective(2, 0, 0, 0);

    	// Fills the stomach with only one bamboo chunk
		stomach.put(Color.GREEN, 1);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted(), "Objective completed with less bamboos than needed");

    	// Fills the stomach with bamboo chunks
		stomach.put(Color.GREEN, 1);
		stomach.put(Color.YELLOW, 1);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted(), "Objective completed with the right amount of bamboos but from different colour");

		stomach.put(Color.YELLOW, 2);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted(), "Objective completed with the right amount of bamboos but from the wrong colour");
    }

    @Test
    @DisplayName("Check we don't complete three colours objectives wrongly")
    void testCheckIfCompletedntThreeColors() {

        // Stub of the mock player
        when(mockPlayer.getStomach()).thenReturn(stomach);

		pandaObjective = new PandaObjective(1,1,1,0);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted(), "Objective completed with an empty stomach");
    }

	/*
	 * When the CheckIfCompleted method is used with a tile and a board (as parameters)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a tile and a board (as parameters)")
	void testCheckIfCompletedWithTileAndBoard() {

		pandaObjective = new PandaObjective(2,0,0,0);


		// Create a first Tile (a first parcel)
		Tile firstTile = new Tile(Color.GREEN);

		// Injects the "pond" Tile in the Board
		Board hashBoard = new HashBoard();
		// Places the first Tile
		hashBoard.set(new Point(1, 1), firstTile);

        assertThrows(UnsupportedOperationException.class, () -> pandaObjective.checkIfCompleted(firstTile, hashBoard));
	}

	@Test
	void objectiveDifferentiationTest() {

		pandaObjective = new PandaObjective(2,0,0,0);

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
	void testGetMotif_trueFirstCase() {

		pandaObjective = new PandaObjective(2,0,0,0);

		// Create a reference motif
		Map<Color, Integer> motif = new EnumMap<>(Color.class);
		motif.put(Color.GREEN, 2);
		motif.put(Color.YELLOW, 0);
		motif.put(Color.PINK, 0);

		assertEquals(pandaObjective.getPatternForCompleting(), motif);
    }

	@Test
    @DisplayName("assert true when return the expected motif depending on colors of the PandaObjective, 2nd case")
	void testGetMotif_trueSecondCase() {

		pandaObjective = new PandaObjective(1,1,1,0);

		// Create a reference motif
		Map<Color, Integer> motif = new EnumMap<>(Color.class);
		motif.put(Color.GREEN, 1);
		motif.put(Color.YELLOW, 1);
		motif.put(Color.PINK, 1);

		assertEquals(pandaObjective.getPatternForCompleting(), motif);
    }

}
