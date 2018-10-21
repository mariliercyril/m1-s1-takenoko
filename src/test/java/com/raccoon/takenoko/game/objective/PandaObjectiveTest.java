package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.HashBoard;

import com.raccoon.takenoko.player.Player;

import java.awt.Point;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;

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
	public static void createsMockPlayer() {

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

		pandaObjective = new PandaObjective(Color.GREEN);
	}

    @Test
    @DisplayName("assert true when pandaObjective is completed, 1st case:"
    		+ "the stomach contains 2 bamboo chunks with the expected color")
    public void testCheckIfCompleted_trueFristCase() {

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

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        pandaObjective.checkIfCompleted(mockPlayer);
        assertFalse(pandaObjective.isCompleted());
    }

	/*
	 * When the CheckIfCompleted method is used with a tile and a board (as paramters)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a tile and a board (as parameters)")
	public void testCheckIfCompletedWithTileAndBoard() {

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

	@Disabled("Not working with this implementation")
	@Test
    void objectiveDifferentiationTest() {
        // So that the mock player returns the stomach
        when(mockPlayer.getStomach()).thenReturn(stomach);

        PandaObjective objective2chunks = new PandaObjective(Color.GREEN);

        stomach.put(Color.GREEN, 1);
        stomach.put(Color.YELLOW, 1);
        stomach.put(Color.PINK, 1);

        objective2chunks.checkIfCompleted(mockPlayer);

        assertFalse( objective2chunks.isCompleted(), "Objective validated with 3 different chunks, even though it was supposed to need 2 chunks of the same color");


    }

}
