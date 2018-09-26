package com.raccoon.takenoko.game.objective;

import java.awt.Point;

import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;

/**
 * This class allows to satisfy the following objective:
 * Place two tiles in an adjacent way.
 */
public class BasicObjective implements Objective {

	private boolean isCompleted;

	public BasicObjective() {

		isCompleted = false;
	}

	@Override
	public boolean isCompleted() {
		return isCompleted;
	}

	@Override
	public boolean checkIfCompleted(Tile basicTile, Board hashBoard) {

		// Gets the position of the basic tile
		Point position = basicTile.getPosition();

		// Gets the tiles which are in the neighbourhood of the tile to be placed
		List<Tile> tiles = hashBoard.getNeighbours(position);

		// Is completed if the tile to be placed has at least one neighbour tile
		isCompleted = (tiles.size() > 0);

		return isCompleted;
	}

    // TODO: To define.
	@Override
	public int getScore() {

		return 0;
	}

}
