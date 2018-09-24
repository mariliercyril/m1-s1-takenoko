package com.raccoon.takenoko.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColorObjectiveTest {

	@Test
	@DisplayName("assert true when color objective is completed")
	public void testCheckIfCompleted() {

		Objective objective = new ColorObjective();

		// Creates a first Tile and injects in the Board
		Tile firstTile = new BasicTile(new Point(0, 0));
		Board hashBoard = new HashBoard(firstTile);
		// The first case
		hashBoard.set(new Point(1, 0), new BasicTile());
		hashBoard.set(new Point(2, 0), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
		// The second case
		hashBoard.set(new Point(1, -1), new BasicTile());
		hashBoard.set(new Point(2, -2), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
		// The third case
		hashBoard.set(new Point(0, 1), new BasicTile());
		hashBoard.set(new Point(0, 2), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));

		// Recreates a first Tile and injects in the Board
		firstTile = new BasicTile(new Point(1, 2));
		hashBoard = new HashBoard(firstTile);
		// The first case
		hashBoard.set(new Point(2, 2), new BasicTile());
		hashBoard.set(new Point(3, 2), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
		// The second case
		hashBoard.set(new Point(2, 1), new BasicTile());
		hashBoard.set(new Point(3, 0), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
		// The third case
		hashBoard.set(new Point(1, 3), new BasicTile());
		hashBoard.set(new Point(1, 4), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
	}
}
