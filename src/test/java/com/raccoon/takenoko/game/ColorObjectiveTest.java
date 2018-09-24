package com.raccoon.takenoko.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.raccoon.takenoko.game.objective.ColorObjective;
import com.raccoon.takenoko.game.objective.Objective;

public class ColorObjectiveTest {

	@Test
	@DisplayName("assert true when color objective is completed")
	public void testIsCompleted() {

		Objective objective = new ColorObjective();

		/*
		 * Tests the three sorts of alignments (without the color).
		 */
		// Creates a first Tile and injects in the Board
		Tile firstTile = new BasicTile(Color.GREEN);
		// The first Tile as the coordinates (0, 0)
		Board hashBoard = new HashBoard(firstTile);
		// The first case
		hashBoard.set(new Point(1, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(2, 0), new BasicTile(Color.GREEN));
		assertTrue(objective.isCompleted(firstTile, hashBoard));
		// The second case
		hashBoard.set(new Point(1, -1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(2, -2), new BasicTile(Color.GREEN));
		assertTrue(objective.isCompleted(firstTile, hashBoard));
		// The third case
		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(0, 2), new BasicTile(Color.GREEN));
		assertTrue(objective.isCompleted(firstTile, hashBoard));

		/*
		 * Tests the color (without testing the alignments).
		 */
		// Recreates a first Tile and injects in the Board
		firstTile = new BasicTile(Color.YELLOW);
		// The first Tile as the coordinates (0, 0)
		hashBoard = new HashBoard(firstTile);
		// A first case
		hashBoard.set(new Point(1, 0), new BasicTile(Color.PINK));
		hashBoard.set(new Point(2, 0), new BasicTile(Color.YELLOW));
		assertFalse(objective.isCompleted(firstTile, hashBoard));
		// A second case
		hashBoard.set(new Point(1, 0), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(2, 0), new BasicTile(Color.PINK));
		assertFalse(objective.isCompleted(firstTile, hashBoard));
		// A third case
		hashBoard.set(new Point(1, 0), new BasicTile(Color.PINK));
		hashBoard.set(new Point(2, 0), new BasicTile(Color.PINK));
		assertFalse(objective.isCompleted(firstTile, hashBoard));
		// A fourth case
		hashBoard.set(new Point(1, 0), new BasicTile(Color.PINK));
		hashBoard.set(new Point(2, 0), new BasicTile(Color.GREEN));
		assertFalse(objective.isCompleted(firstTile, hashBoard));

		/*
		 * Tests the color and alignment.
		 */
		// Recreates, a second time, a first Tile and injects in the Board
		firstTile = new BasicTile(Color.PINK);
		// The first Tile as the coordinates (0, 0)
		hashBoard = new HashBoard(firstTile);
		// A case
		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(2, 0), new BasicTile(Color.PINK));
		assertFalse(objective.isCompleted(firstTile, hashBoard));
	}
}
