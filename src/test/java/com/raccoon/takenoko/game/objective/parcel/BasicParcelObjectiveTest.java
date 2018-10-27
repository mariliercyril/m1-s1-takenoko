package com.raccoon.takenoko.game.objective.parcel;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.HashBoard;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class allows to test the method {@code checkIfCompleted} of the class {@link BasicParcelObjective}.
 */
public class BasicParcelObjectiveTest {

	private static Tile pondTile;
	private Board hashBoard;

	private static Tile initialTile;

	private BasicParcelObjective basicParcelObjective;

	@BeforeAll
	public static void constructPondTile() {

		pondTile = new Tile();
		initialTile = new Tile(Color.YELLOW);
	}

	@BeforeEach
	public void initialize() {

		// Injects the "pond" Tile (first Tile) in the Board
		hashBoard = new HashBoard(pondTile);

		// Places the initial Tile (the first plot)
		hashBoard.set(new Point(1, 1), initialTile);

		basicParcelObjective = new BasicParcelObjective();
	}

	@Test
	@DisplayName("assert true when basic objective is completed, 1st case: from the \"pond\" Tile to the initial Tile")
	public void testIsCompleted_truePondToInitialCase() {

		basicParcelObjective.checkIfCompleted(pondTile, hashBoard);
		assertTrue(basicParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when basic objective is completed, 2nd case: from the initial Tile to the \"pond\" Tile")
	public void testIsCompleted_trueInitialToPondCase() {

		basicParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(basicParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when basic objective is completed, 3rd case: from the initial Tile to another Tile")
	public void testIsCompleted_trueInitialToAnotherCase() {

		hashBoard.set(new Point(2, 2), new Tile(Color.YELLOW));

		basicParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(basicParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when basic objective is not completed: from another Tile to a \"lambda\" Tile")
	public void testIsCompleted_falseAnotherToLambdaCase() {

		Tile anotherTile = new Tile(Color.YELLOW);
		hashBoard.set(new Point(3, 3), anotherTile);
		// A lambda Tile
		hashBoard.set(new Point(-1, -1), new Tile(Color.YELLOW));

		basicParcelObjective.checkIfCompleted(anotherTile, hashBoard);
		assertFalse(basicParcelObjective.isCompleted());
	}

}
