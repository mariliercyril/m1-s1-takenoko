package com.raccoon.takenoko.game;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicObjectiveTest {

	@Test
	@DisplayName("assert true when basic objective is completed")
	public void testCheckIfCompleted() {

		Objective objective = new BasicObjective();

		// Creates a first Tile and injects in the Board
		Tile firstTile = new BasicTile(new Point(0, 0));
		Board hashBoard = new HashBoard(firstTile);
		hashBoard.set(new Point(0, 1), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
	}
}
