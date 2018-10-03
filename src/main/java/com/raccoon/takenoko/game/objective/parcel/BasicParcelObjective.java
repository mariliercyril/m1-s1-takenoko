package com.raccoon.takenoko.game.objective.parcel;

import java.awt.Point;

import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.game.objective.Objective;

import com.raccoon.takenoko.player.Player;

/**
 * This class allows to satisfy the following objective:
 * Place two tiles in an adjacent way.
 */
public class BasicParcelObjective extends Objective {

	public BasicParcelObjective() {

		super();

		score = 1;
	}

	@Override
	public void checkIfCompleted(Tile tileToBePlaced, Board hashBoard) {

		// Gets the position of the tile to be placed
		Point position = tileToBePlaced.getPosition();

		// Gets the tiles which are in the neighbourhood of the tile to be placed
		List<Tile> tiles = hashBoard.getNeighbours(position);

		// Is completed if the tile to be placed has at least one neighbour tile
		isCompleted = (!(tiles.isEmpty()));
	}

	@Override
	public void checkIfCompleted(Player player) throws UnsupportedOperationException {

		new UnsupportedOperationException();
	}

}
