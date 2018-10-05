package com.raccoon.takenoko.game.objective.parcel;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.game.objective.Objective;

import com.raccoon.takenoko.tool.Vector;

/**
 * This class allows to satisfy the following objective:
 * Align three tiles of the same color.
 */
public class AlignmentParcelObjective extends Objective {

	private static final int SCORE_BASE = 2;

	public AlignmentParcelObjective(Color color) {

		super();
		this.color = color;
		setScore(color);
	}

	@Override
	public void checkIfCompleted(Tile tileToBePlaced, Board hashBoard) {

		List<Tile> tiles = new ArrayList<>();
		// Initializes the list (with the Tile to be placed)
		tiles.add(tileToBePlaced);

		/*
		 * ARE ALIGNED
		 */
		boolean areAligned = false;
		Point tileToBePlacedPosition = tileToBePlaced.getPosition();
		// The position of the "pond" Tile: No alignment allowed with it
		Point originPoint = new Point(0, 0);
		// Gets the neighbours of the Tile to be placed
		List<Tile> tileToBePlacedNeighbours = hashBoard.getNeighbours(tileToBePlacedPosition);
		Iterator<Tile> tileToBePlacedNeighboursIterator = tileToBePlacedNeighbours.iterator();
		// Parses the neighbours of the Tile to be placed, in order to find a second Tile for alignment
		while (tileToBePlacedNeighboursIterator.hasNext()) {
			Tile secondTile = tileToBePlacedNeighboursIterator.next();
			Point secondTilePosition = secondTile.getPosition();
			if (!(secondTilePosition.equals(originPoint))) {
				// The Vector of translation, from the Tile to be placed to a second Tile, for alignment...
				Vector translationVector = new Vector(tileToBePlacedPosition, secondTilePosition);
				// Parses the neighbours of the Tile to be placed, in order to find the third Tile for alignment
				while (tileToBePlacedNeighboursIterator.hasNext() && !areAligned) {
					Tile thirdTile = tileToBePlacedNeighboursIterator.next();
					Point thirdTilePosition = thirdTile.getPosition();
					if (!(thirdTilePosition.equals(originPoint))
							&& (new Vector(tileToBePlacedPosition, thirdTilePosition)).equals(translationVector.getOpposite())) {
						tiles.add(secondTile);
						tiles.add(thirdTile);
						areAligned = true;
					}
				}
				// Gets the neighbours of each neighbour of the Tile to be placed
				List<Tile> secondTileNeighbours = hashBoard.getNeighbours(secondTilePosition);
				Iterator<Tile> secondTileNeighboursIterator = secondTileNeighbours.iterator();
				// Parses the neighbours of each neighbour of the Tile to be placed, in order to find the third Tile for alignment
				while (secondTileNeighboursIterator.hasNext() && !areAligned) {
					Tile thirdTile = secondTileNeighboursIterator.next();
					Point thirdTilePosition = thirdTile.getPosition();
					if (!(thirdTilePosition.equals(originPoint))
							&& (new Vector(secondTilePosition, thirdTilePosition)).equals(translationVector)) {
						tiles.add(secondTile);
						tiles.add(thirdTile);
						areAligned = true;
					}
				}
			}
		}

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
	 */
	private void setScore(Color color) {

		score = SCORE_BASE + color.ordinal();
	}

}
