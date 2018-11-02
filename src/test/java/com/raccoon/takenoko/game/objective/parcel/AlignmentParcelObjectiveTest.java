package com.raccoon.takenoko.game.objective.parcel;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.HashBoard;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

import com.raccoon.takenoko.player.Player;

import java.awt.Point;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class allows to test the method {@code checkIfCompleted} of the class {@link AlignmentParcelObjective}.
 */
public class AlignmentParcelObjectiveTest {

	private static Tile pondTile;
	private Board hashBoard;

	private Tile firstTile;

	private AlignmentParcelObjective alignmentParcelObjective;

	@BeforeAll
	public static void constructPondTile() {

		pondTile = new Tile();
	}

	@BeforeEach
	public void initialize() {

		// Injects the "pond" Tile (initial Tile) in the Board
		hashBoard = new HashBoard(pondTile);
	}

	private void setTest(Point position, Color color) {

		firstTile = new Tile(color);

		// Places the first Tile (the first parcel)
		hashBoard.set(position, firstTile);

		alignmentParcelObjective = new AlignmentParcelObjective(color);
	}

	/*
	 * When alignmentParcelObjective is completed (the three tiles in question ARE ALIGNED and HAVE SAME COLOR)
	 */
	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 1st case: by I")
	public void testCheckIfCompleted_trueFirstCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(2, 1), new Tile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 2nd case: by J")
	public void testCheckIfCompleted_trueSecondCase() {

		setTest(new Point(1, 1), Color.YELLOW);

		hashBoard.set(new Point(1, 2), new Tile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new Tile(Color.YELLOW));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 3rd case: by K")
	public void testCheckIfCompleted_trueThirdCase() {

		setTest(new Point(1, 1), Color.PINK);

		hashBoard.set(new Point(2, 2), new Tile(Color.PINK));
		hashBoard.set(new Point(3, 3), new Tile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 4th case: by -I")
	public void testCheckIfCompleted_trueFourthCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(0, 1), new Tile(Color.GREEN));
		hashBoard.set(new Point(-1, 1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 5th case: by -J")
	public void testCheckIfCompleted_trueFifthCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(1, 0), new Tile(Color.GREEN));
		hashBoard.set(new Point(1, -1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 6th case: by -K")
	public void testCheckIfCompleted_trueSixthCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(2, 2), new Tile(Color.GREEN));
		hashBoard.set(new Point(3, 3), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 7th case: by I (with the first Tile in the middle)")
	public void testCheckIfCompleted_trueSeventhCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(0, 1), new Tile(Color.GREEN));
		hashBoard.set(new Point(2, 1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When alignmentParcelObjective is not completed (the three tiles in question ARE ALIGNED but HAVE NOT SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when have not same color, 1st case: the second Tile has not same color")
	public void testCheckIfCompleted_falseColorFirstCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(1, 2), new Tile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when have not same color, 2nd case: the third Tile has not same color")
	public void testCheckIfCompleted_falseColorSecondCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(1, 2), new Tile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new Tile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when have not same color, 3rd case: have not same color")
	public void testCheckIfCompleted_falseColorThirdCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(1, 2), new Tile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new Tile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When alignmentParcelObjective is not completed (the three tiles in question ARE NOT ALIGNED although HAVE SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when are not aligned, 1st case")
	public void testCheckIfCompleted_falseAlignmentFirstCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(1, 2), new Tile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When alignmentParcelObjective is not completed (the three tiles in question ARE NOT ALIGNED, HAVE NOT SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when are not aligned & have not same color")
	public void testCheckIfCompleted_falseColorAndAlignment() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(1, 2), new Tile(Color.YELLOW));
		hashBoard.set(new Point(3, 1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When two tiles have same position
	 */
	@Test
	@DisplayName("assert false when have same color, but at least two tiles have same position (normally unfeasible case)")
	public void testCheckIfCompleted_falseSamePositionCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(0, 1), new Tile(Color.GREEN));
		hashBoard.set(new Point(1, 1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When the "pond" Tile is considered to complete the alignmentParcelObjective
	 */
	@Test
	@DisplayName("assert false when the \"pond\" Tile is considered to complete the alignmentParcelObjective (normally unfeasible case)")
	public void testCheckIfCompleted_falsePondTileCase() {

		setTest(new Point(1, 1), Color.GREEN);

		hashBoard.set(new Point(0, 0), new Tile(Color.GREEN));
		hashBoard.set(new Point(-1, -1), new Tile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When the CheckIfCompleted method is used with a player (s a parameter)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a player (as a parameter)")
	public void testCheckIfCompletedWithPlayer() {

		setTest(new Point(1, 1), Color.GREEN);

		// Creates a stomach
		HashMap<Color, Integer> stomach = new HashMap<>();

        // Creates a mock player
        Player mockPlayer = mock(Player.class);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        assertThrows(UnsupportedOperationException.class, () -> alignmentParcelObjective.checkIfCompleted(mockPlayer));
	}

	/*
	 * When the CheckIfCompleted method is used with a board (as a parameter)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a board (as a parameter)")
	public void testCheckIfCompletedWithBoard() {

		setTest(new Point(1, 1), Color.GREEN);

        assertThrows(UnsupportedOperationException.class, () -> alignmentParcelObjective.checkIfCompleted(hashBoard));
	}

}
