package com.raccoon.takenoko.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColorObjectiveTest {

	private Tile firstTile = new BasicTile(new Point(0, 0));

	private Board hashBoard = new HashBoard(firstTile);

	@Test
	@DisplayName("assert true when basic objective is completed")
	public void testCheckIfCompleted() {

		hashBoard.set(new Point(1, 0), new BasicTile());
		hashBoard.set(new Point(2, 0), new BasicTile());

		Objective objective = new ColorObjective();
		assertTrue(objective.isCompleted(firstTile, hashBoard));
	}
}
