package com.raccoon.takenoko.game.objective.parcel;

import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.Board;

import com.raccoon.takenoko.game.objective.Objective;

import java.awt.Point;

import java.util.List;

/**
 * The {@code BasicParcelObjective} class implements the <i>parcel</i> {@link Objective}
 * which consists in "<b>having placed two tiles in an adjacent way</b>".
 * 
 * <p>This objective was used while waiting for real objectives of the game;
 * consequently, there is a single score (equal to 1).</p>
 */
public class BasicParcelObjective extends Objective {

	/**
	 * Constructs a {@code BasicParcelObjective}.
	 */
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

}
