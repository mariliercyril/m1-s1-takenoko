package com.raccoon.takenoko.game.objective;

import java.awt.Point;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.raccoon.takenoko.game.BasicTile;
import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.HashBoard;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.objective.BasicObjective;
import com.raccoon.takenoko.game.objective.Objective;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicObjectiveTest {

	@Test
	@DisplayName("assert true when basic objective is completed")
	public void testIsCompleted() {

		Objective objective = new BasicObjective();

		// Creates a first Tile and injects in the Board
		Tile firstTile = new BasicTile(new Point(0, 0));
		Board hashBoard = new HashBoard(firstTile);
		hashBoard.set(new Point(0, 1), new BasicTile());
		assertTrue(objective.isCompleted(firstTile, hashBoard));
	}
}
