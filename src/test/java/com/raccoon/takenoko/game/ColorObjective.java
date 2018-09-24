package com.raccoon.takenoko.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColorObjective {

	private Tile firstTile = new BasicTile(new Point(1, 2));

	private Board hashBoard = new HashBoard(firstTile);

	@Test
	@DisplayName("assert true when basic objective is completed")
	public void testCheckIfCompleted() {

		hashBoard.set(new Point(2, 2), new BasicTile());
		hashBoard.set(new Point(3, 2), new BasicTile());

		hashBoard.set(new Point(2, 1), new BasicTile());
		hashBoard.set(new Point(3, 0), new BasicTile());

		hashBoard.set(new Point(1, 3), new BasicTile());
		hashBoard.set(new Point(1, 4), new BasicTile());

		Objective objective = new BasicObjective();
		assertTrue(objective.checkIfCompleted(firstTile, hashBoard));
	}
}
