package com.raccoon.takenoko.game.objective.parcel;

import java.awt.Point;

import java.util.ArrayList;
import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.game.objective.Objective;

import com.raccoon.takenoko.tool.Vector;

/**
 * The {@code AlignmentParcelObjective} class implements the <i>parcel</i> {@link Objective}
 * which consists in "<b>having aligned three tiles of the same color</b>".
 * <p>
 * The score base is equal to 2; consequently, the scores are:
 * <ul>
 * <li>2 for GREEN tiles</li>
 * <li>3 for YELLOW tiles</li>
 * <li>4 for PINK tiles</li>
 * </ul>
 */
public class AlignmentParcelObjective extends Objective {

	// The position of the "pond" Tile: No alignment allowed with it
	private static final Point ORIGIN_POINT = new Point(0, 0);

	private static final int SCORE_BASE = 2;

	private boolean areAligned;

	/**
	 * Constructs a {@code AlignmentParcelObjective} with a specified color.
	 * 
	 * @param color
	 *  the color of the parcels which should be aligned
	 */
	public AlignmentParcelObjective(Color color) {

		super();
		this.color = color;
		setScore(color);

		areAligned = false;
	}

	@Override
	public void checkIfCompleted(Tile tileToBePlaced, Board hashBoard) {

		List<Tile> tiles = new ArrayList<>();
		// Initializes the list (with the Tile to be placed)
		tiles.add(tileToBePlaced);

		/*
		 * ARE ALIGNED
		 */
		Point tileToBePlacedPosition = tileToBePlaced.getPosition();
		// Gets the neighbours of the Tile to be placed
		List<Tile> tileToBePlacedNeighbours = hashBoard.getNeighbours(tileToBePlacedPosition);
		// Parses the neighbours of the Tile to be placed, in order to find a second Tile for alignment
		tileToBePlacedNeighbours.stream().filter(t2 -> !areAligned).forEach(t2 -> {
			Point secondTilePosition = t2.getPosition();
			if (!(secondTilePosition.equals(ORIGIN_POINT))) {
				// The Vector of translation, from the Tile to be placed to a second Tile, for alignment...
				Vector translationVector = new Vector(tileToBePlacedPosition, secondTilePosition);
				// Parses the neighbours of the Tile to be placed, in order to find the third Tile for alignment
				tileToBePlacedNeighbours.stream().filter(t3 -> !(t3.equals(t2))).forEach(t3 -> {
					Point thirdTilePosition = t3.getPosition();
					if (!(thirdTilePosition.equals(ORIGIN_POINT))
							&& (new Vector(tileToBePlacedPosition, thirdTilePosition)).equals(translationVector.getOpposite())) {
						tiles.add(t2);
						tiles.add(t3);
						areAligned = true;
					}
				});
				// Gets the neighbours of each neighbour of the Tile to be placed
				List<Tile> secondTileNeighbours = hashBoard.getNeighbours(secondTilePosition);
				// Parses the neighbours of each neighbour of the Tile to be placed, in order to find the third Tile for alignment
				secondTileNeighbours.stream().filter(t3 -> !(t3.equals(tileToBePlaced))).forEach(t3 -> {
					Point thirdTilePosition = t3.getPosition();
					if (!(thirdTilePosition.equals(ORIGIN_POINT))
							&& (new Vector(secondTilePosition, thirdTilePosition)).equals(translationVector)) {
						tiles.add(t2);
						tiles.add(t3);
						areAligned = true;
					}
				});
			}
		});

		/*
		 * HAVE SAME COLOR (if are aligned)
		 */
		if (areAligned && ((tiles.stream()).allMatch(t -> (t.getColor()).equals(color)))) {
			isCompleted = true;
		}
	}

	/**
	 * Sets the score according to the color.
	 * 
	 * @param color
	 * 	the color which is associated with the objective
	 */
	private void setScore(Color color) {

		score = SCORE_BASE + color.ordinal();
	}

}
