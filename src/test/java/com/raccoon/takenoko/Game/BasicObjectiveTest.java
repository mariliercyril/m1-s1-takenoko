package com.raccoon.takenoko.Game;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicObjectiveTest {

	private Tile firstTile = new BasicTile(new Point(0, 0));

	private Board hashBoard = new HashBoard(firstTile);

	@Test
	@DisplayName("assert true when basic objective is completed")
	public void testCheckIfCompleted() {

		hashBoard.set(new Point(0, 1), new BasicTile());

		Objective objective = new BasicObjective();
		assertTrue(objective.checkIfCompleted(firstTile, hashBoard));
	}
}
