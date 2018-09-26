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
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.HashBoard;
import com.raccoon.takenoko.game.Tile;

/**
 * This class allows to perform unit tests of the methods (apart from "get()" and "set()")
 * of the class {@code ColorObjective}.
 */
public class ColorObjectiveTest {

	private static Tile pondTile;

	private static Tile initialTile;
	private Board hashBoard;

	private Objective colorObjective;

	@BeforeAll
	public static void constructPondTile() {

		pondTile = new BasicTile();
		initialTile = new BasicTile(Color.GREEN);
	}

	@BeforeEach
	public void initialize() {

		// Injects the "pond" Tile (first Tile) in the Board
		hashBoard = new HashBoard(pondTile);

		// Places the initial Tile (the first plot)
		hashBoard.set(new Point(1, 1), initialTile);

		colorObjective = new ColorObjective();
	}

	/*
	 * When color objective is completed
	 */
	@Test
	@DisplayName("assert true when color objective is completed, 1st case: by I")
	public void testIsCompleted_trueFirstCase() {

		hashBoard.set(new Point(2, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert true when color objective is completed, 2nd case: by J")
	public void testIsCompleted_trueSecondCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert true when color objective is completed, 3rd case: by K")
	public void testIsCompleted_trueThirdCase() {

		hashBoard.set(new Point(2, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 3), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert true when color objective is completed, 4th case: by -I")
	public void testIsCompleted_trueFourthCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(-1, 1), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert true when color objective is completed, 5th case: by -J")
	public void testIsCompleted_trueFifthCase() {

		hashBoard.set(new Point(1, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, -1), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert true when color objective is completed, 6th case: by -K")
	public void testIsCompleted_trueSixthCase() {

		hashBoard.set(new Point(0, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(-1, -1), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert true when color objective is completed, 7th case: by I (with the initial Tile in the middle)")
	public void testIsCompleted_trueSeventhCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(2, 1), new BasicTile(Color.GREEN));
		assertTrue(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	/*
	 * When color is not satisfied
	 */
	@Test
	@DisplayName("assert false when color is not satisfied, 1st case: by I")
	public void testIsCompleted_falseColorFirstCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.GREEN));
		assertFalse(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert false when color is not satisfied, 2nd case: by J")
	public void testIsCompleted_falseColorSecondCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.PINK));
		assertFalse(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	@Test
	@DisplayName("assert false when color is not satisfied, 3rd case: by K")
	public void testIsCompleted_falseColorThirdCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.PINK));
		assertFalse(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	/*
	 * When alignment is not satisfied
	 */
	@Test
	@DisplayName("assert false when alignment is not satisfied")
	public void testIsCompleted_falseAlignment() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));
		assertFalse(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	/*
	 * When neither color nor alignment are satisfied
	 */
	@Test
	@DisplayName("assert false when neither color nor alignment are satisfied")
	public void testIsCompleted_falseColorAlignment() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));
		assertFalse(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

	/*
	 * When two tiles have the same position
	 */
	@Test
	@DisplayName("assert false when the color is completed, but two tiles have the same position (normally unfeasible case)")
	public void testIsCompleted_falseSamePositionCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 1), new BasicTile(Color.GREEN));
		assertFalse(colorObjective.checkIfCompleted(initialTile, hashBoard));
	}

}
