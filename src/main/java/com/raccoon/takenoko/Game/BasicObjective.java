package com.raccoon.takenoko.Game;

import java.awt.Point;

import java.util.List;

/**
 * This class allows to define a basic (first) objective as follows:
 * To place two tiles in an adjacent way.
 */
public class BasicObjective implements Objective {

	private boolean isCompleted;

	BasicObjective() {

		isCompleted = false;
	}

	/**
	 * Check if the basic objective is completed.
	 */
	@Override
	public boolean checkIfCompleted(Tile basicTile, Board hashBoard) {

		// Gets the position of the tile to be placed
		Point position = basicTile.getPosition();

		// Gets the tiles which are in the neighbourhood of the tile to be placed
		List<Tile> tiles = hashBoard.getNeighbours(position);

		// Is completed if the tile to be placed has at least two tiles
		// which are in the neighbourhood
		isCompleted = (tiles.size() > 1);

		return isCompleted;
	}

    // TODO: To define.
	@Override
	public int getScore() {

		return 0;
	}
}
