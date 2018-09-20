package com.raccoon.takenoko.Game;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicObjectiveTest {

	private Tile firstTile;

	private Board hashBoard;

	@BeforeAll
	public void initialize() {

		firstTile = new BasicTile(new Point(0, 0));

		hashBoard = new HashBoard(firstTile);

		hashBoard.set(new Point(0, 1), new BasicTile());
	}

	@Test
	public void testCheckIfCompleted() {

		Objective objective = new BasicObjective();
		assertTrue(objective.checkIfCompleted(firstTile, hashBoard));
	}
}
