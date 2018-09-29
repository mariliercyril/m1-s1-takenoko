package com.raccoon.takenoko.game.objective;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.raccoon.takenoko.game.BasicTile;
import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.HashBoard;
import com.raccoon.takenoko.game.Tile;

/**
 * This class allows to perform unit tests of the methods (apart from "get()" and "set()")
 * of the class {@code BasicObjective}.
 */
public class BasicObjectiveTest {

	private static Tile pondTile;

	private static Tile initialTile;
	private Board hashBoard;

	private Objective basicObjective;

	@BeforeAll
	public static void constructPondTile() {

		pondTile = new BasicTile();
		initialTile = new BasicTile();
	}

	@BeforeEach
	public void initialize() {

		// Injects the "pond" Tile (first Tile) in the Board
		hashBoard = new HashBoard(pondTile);

		// Places the initial Tile (the first plot)
		hashBoard.set(new Point(1, 1), initialTile);

		basicObjective = new BasicObjective();
	}

	@Test
	@DisplayName("assert true when basic objective is completed, 1st case: from the \"pond\" Tile to the initial Tile")
	public void testIsCompleted_truePondToInitialCase() {

		basicObjective.checkIfCompleted(pondTile, hashBoard);
		assertTrue(basicObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when basic objective is completed, 2nd case: from the initial Tile to the \"pond\" Tile")
	public void testIsCompleted_trueInitialToPondCase() {

		basicObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(basicObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when basic objective is completed, 3rd case: from the initial Tile to another Tile")
	public void testIsCompleted_trueInitialToAnotherCase() {

		hashBoard.set(new Point(2, 2), new BasicTile());

		basicObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(basicObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when basic objective is not completed: from another Tile to a \"lambda\" Tile")
	public void testIsCompleted_falseAnotherToLambdaCase() {

		Tile anotherTile = new BasicTile();
		hashBoard.set(new Point(3, 3), anotherTile);
		// A lambda Tile
		hashBoard.set(new Point(-1, -1), new BasicTile());

		basicObjective.checkIfCompleted(anotherTile, hashBoard);
		assertFalse(basicObjective.isCompleted());
	}

}
