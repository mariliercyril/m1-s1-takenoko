package com.raccoon.takenoko.game.objective.parcel;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import java.util.HashMap;

import com.raccoon.takenoko.game.BasicTile;
import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.HashBoard;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.player.Player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class allows to test the method {@code checkIfCompleted} of the class {@link AlignmentParcelObjective}.
 */
public class AlignmentParcelObjectiveTest {

	private static Tile pondTile;
	private Board hashBoard;

	private static Tile firstTile;

	private AlignmentParcelObjective alignmentParcelObjective;

	@BeforeAll
	public static void constructPondTile() {

		pondTile = new BasicTile();
		firstTile = new BasicTile(Color.GREEN);
	}

	@BeforeEach
	public void initialize() {

		// Injects the "pond" Tile (initial Tile) in the Board
		hashBoard = new HashBoard(pondTile);

		// Places the first Tile (the first parcel)
		hashBoard.set(new Point(1, 1), firstTile);

		alignmentParcelObjective = new AlignmentParcelObjective(Color.GREEN);
	}

	/*
	 * When alignmentParcelObjective is completed (the three tiles in question ARE ALIGNED and HAVE SAME COLOR)
	 */
	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 1st case: by I")
	public void testCheckIfCompleted_trueFirstCase() {

		hashBoard.set(new Point(2, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 2nd case: by J")
	public void testCheckIfCompleted_trueSecondCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 3rd case: by K")
	public void testCheckIfCompleted_trueThirdCase() {

		hashBoard.set(new Point(2, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 4th case: by -I")
	public void testCheckIfCompleted_trueFourthCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(-1, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 5th case: by -J")
	public void testCheckIfCompleted_trueFifthCase() {

		hashBoard.set(new Point(1, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, -1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 6th case: by -K")
	public void testCheckIfCompleted_trueSixthCase() {

		hashBoard.set(new Point(2, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert true when alignmentParcelObjective is completed, 7th case: by I (with the first Tile in the middle)")
	public void testCheckIfCompleted_trueSeventhCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(2, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertTrue(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When alignmentParcelObjective is not completed (the three tiles in question ARE ALIGNED but HAVE NOT SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when have not same color, 1st case: the second Tile has not same color")
	public void testCheckIfCompleted_falseColorFirstCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when have not same color, 2nd case: the third Tile has not same color")
	public void testCheckIfCompleted_falseColorSecondCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	@Test
	@DisplayName("assert false when have not same color, 3rd case: have not same color")
	public void testCheckIfCompleted_falseColorThirdCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(1, 3), new BasicTile(Color.PINK));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When alignmentParcelObjective is not completed (the three tiles in question ARE NOT ALIGNED although HAVE SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when are not aligned")
	public void testCheckIfCompleted_falseAlignmentFirstCase() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When alignmentParcelObjective is not completed (the three tiles in question ARE NOT ALIGNED, HAVE NOT SAME COLOR)
	 */
	@Test
	@DisplayName("assert false when are not aligned & have not same color")
	public void testCheckIfCompleted_falseColorAndAlignment() {

		hashBoard.set(new Point(1, 2), new BasicTile(Color.YELLOW));
		hashBoard.set(new Point(3, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When two tiles have same position
	 */
	@Test
	@DisplayName("assert false when have same color, but at least two tiles have same position (normally unfeasible case)")
	public void testCheckIfCompleted_falseSamePositionCase() {

		hashBoard.set(new Point(0, 1), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(1, 1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When the "pond" Tile is considered to complete the alignmentParcelObjective
	 */
	@Test
	@DisplayName("assert false when the \"pond\" Tile is considered to complete the alignmentParcelObjective (normally unfeasible case)")
	public void testCheckIfCompleted_falsePondTileCase() {

		hashBoard.set(new Point(0, 0), new BasicTile(Color.GREEN));
		hashBoard.set(new Point(-1, -1), new BasicTile(Color.GREEN));

		alignmentParcelObjective.checkIfCompleted(firstTile, hashBoard);
		assertFalse(alignmentParcelObjective.isCompleted());
	}

	/*
	 * When the CheckIfCompleted method is used with a player (s a paramter)
	 */
	@Test
	@DisplayName("assert that it throws UnsupportedOperationException when the CheckIfCompleted method is used with a player (as a paramter)")
	public void testCheckIfCompletedWithPlayer() {

		// Creates a stomach
		HashMap<Color, Integer> stomach = new HashMap<>();

        // Creates a mock player
        Player mockPlayer = mock(Player.class);

        // So that the mock player returns the stomach 
        when(mockPlayer.getStomach()).thenReturn(stomach);

        assertThrows(UnsupportedOperationException.class, () -> alignmentParcelObjective.checkIfCompleted(mockPlayer));
	}

}
