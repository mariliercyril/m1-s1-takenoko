package com.raccoon.takenoko.game.objective;

import java.awt.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.tool.Vector;

/**
 * This class allows to satisfy the following objective:
 * Align three tiles of the same color.
 */
public class ColorObjective implements Objective {

	private boolean isCompleted;

	public ColorObjective() {

		isCompleted = false;
	}

	@Override
	public boolean isCompleted() {

		return isCompleted;
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
		// Gets the neighbours of the Tile to be placed
		List<Tile> tileToBePlacedNeighbours = hashBoard.getNeighbours(tileToBePlaced.getPosition());
		Iterator<Tile> tileToBePlacedNeighboursIterator = tileToBePlacedNeighbours.iterator();
		// Parses the neighbours of the Tile to be placed, in order to find a second Tile for alignment
		while (tileToBePlacedNeighboursIterator.hasNext()) {
			Tile secondTile = tileToBePlacedNeighboursIterator.next();
			Point secondTilePosition = secondTile.getPosition();
			if (!(secondTilePosition.equals(new Point(0, 0)))) {
				// The Vector of translation, from the Tile to be placed to a second Tile, for alignment...
				Vector translationVector = new Vector(tileToBePlaced.getPosition(), secondTile.getPosition());
				// Parses the neighbours of the Tile to be placed, in order to find the third Tile for alignment
				while (tileToBePlacedNeighboursIterator.hasNext()) {
					Tile thirdTile = tileToBePlacedNeighboursIterator.next();
					Point thirdTilePosition = thirdTile.getPosition();
					if (!(thirdTilePosition.equals(new Point(0, 0)))) {
						if ((new Vector(tileToBePlaced.getPosition(), thirdTile.getPosition())).equals(translationVector.getOpposite())) {
							tiles.add(secondTile);
							tiles.add(thirdTile);
							areAligned = true;
						}
					}
				}
				// Gets the neighbours of each neighbour of the Tile to be placed
				List<Tile> secondTileNeighbours = hashBoard.getNeighbours(secondTile.getPosition());
				Iterator<Tile> secondTileNeighboursIterator = secondTileNeighbours.iterator();
				// Parses the neighbours of each neighbour of the Tile to be placed, in order to find the third Tile for alignment
				while (secondTileNeighboursIterator.hasNext()) {
					Tile thirdTile = secondTileNeighboursIterator.next();
					Point thirdTilePosition = thirdTile.getPosition();
					if (!(thirdTilePosition.equals(new Point(0, 0)))) {
						if ((new Vector(secondTile.getPosition(), thirdTile.getPosition())).equals(translationVector)) {
							tiles.add(secondTile);
							tiles.add(thirdTile);
							areAligned = true;
						}
					}
				}
			}
		}

		/*
		 * HAVE SAME COLOR (if are aligned)
		 */
		if (areAligned) {
			boolean haveSameColor = false;
			for (Color color : Color.values()) {
				haveSameColor |= tiles.stream().allMatch(t -> (t.getColor()).equals(color));
			}

			isCompleted = haveSameColor;
		}
	}

    // TODO: To define.
	@Override
	public int getScore() {

		return 0;
	}

}
