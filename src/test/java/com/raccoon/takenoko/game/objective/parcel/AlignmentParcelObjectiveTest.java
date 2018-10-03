package com.raccoon.takenoko.game.objective.parcel;

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
 * This class allows to perform unit tests of the method "checkIfCompleted()"
 * of the class {@code AlignmentParcelObjective}.
 */
public class AlignmentParcelObjectiveTest {

	private static Tile pondTile;
	private Board hashBoard;

	private static Tile initialTile;

	private AlignmentParcelObjective alignmentParcelObjective;

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

		alignmentParcelObjective = new AlignmentParcelObjective(Color.GREEN);
	}

	/*
	 * When color objective is completed (the three tiles in question ARE ALIGNED and HAVE SAME COLOR)
	 */
	@Test
	@DisplayName("assert true when color objective is completed, 1st case: by I")
	public void testIsCompleted_trueFirstCase() {

		hashBoard.set(new Point(2, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when color objective is completed, 2nd case: by J")
	public void testIsCompleted_trueSecondCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when color objective is completed, 3rd case: by K")
	public void testIsCompleted_trueThirdCase() {

		hashBoard.set(new Point(2, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when color objective is completed, 4th case: by -I")
	public void testIsCompleted_trueFourthCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(-1, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when color objective is completed, 5th case: by -J")
	public void testIsCompleted_trueFifthCase() {

		hashBoard.set(new Point(1, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, -1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when color objective is completed, 6th case: by -K")
	public void testIsCompleted_trueSixthCase() {

		hashBoard.set(new Point(2, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when color objective is completed, 7th case: by I (with the initial Tile in the middle)")
	public void testIsCompleted_trueSeventhCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(2, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When color objective is not completed (the three tiles in question ARE ALIGNED but HAVE NOT SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when have not same color, 1st case: the second Tile has not same color")
	public void testIsCompleted_falseColorFirstCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when have not same color, 2nd case: the third Tile has not same color")
	public void testIsCompleted_falseColorSecondCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when have not same color, 3rd case: have not same color")
	public void testIsCompleted_falseColorThirdCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When color objective is not completed (the three tiles in question ARE NOT ALIGNED although HAVE SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when are not aligned")
	public void testIsCompleted_falseAlignmentFirstCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When color objective is not completed (the three tiles in question ARE NOT ALIGNED, HAVE NOT SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when are not aligned & have not same color")
	public void testIsCompleted_falseColorAndAlignment() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When two tiles have same position
	 */
	@Test
	@DisplayName("assert false when have same color, but at least two tiles have same position (normally unfeasible case)")
	public void testIsCompleted_falseSamePositionCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When the "pond" Tile is considered to complete the (color) objective
	 */
	@Test
	@DisplayName("assert false when the \"pond\" Tile is considered to complete the (color) objective (normally unfeasible case)")
	public void testIsCompleted_falsePondTileCase() {

		hashBoard.set(new Point(0, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(-1, -1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(initialTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

}
