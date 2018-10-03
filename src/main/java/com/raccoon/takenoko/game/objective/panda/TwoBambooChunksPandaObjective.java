package com.raccoon.takenoko.game.objective.panda;

import java.util.HashMap;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.game.objective.Objective;

import com.raccoon.takenoko.player.Player;

/**
 * This class allows to satisfy the following objective:
 * Have eaten (at least) two bamboo chunks of which color is an expected color.
 */
public class TwoBambooChunksPandaObjective extends Objective {

	private Color color;

	public TwoBambooChunksPandaObjective(Color color) {

		super();
		this.color = color;

		switch (color) {
			case GREEN:
				score = 3;
				break;
			case YELLOW:
				score = 4;
				break;
			case PINK:
				score = 5;
				break;
			default:
				// Do nothing
		}
	}

	@Override
	public void checkIfCompleted(Player player) {

		// Gets the size of the bamboo which is on the final tile
		HashMap<Color, Integer> stomach = player.getStomach();

		// Is completed if the size of the bamboo which is on the final tile is at least equal to 2
		// and the color is the expected color (color of Objective)
		if (stomach.entrySet().stream().anyMatch(b -> ((b.getKey()).equals(color) && (b.getValue()) >= 2))) {
			isCompleted = true;
		}
	}

	@Override
	public void checkIfCompleted(Tile finalTile, Board hashBoard) throws UnsupportedOperationException {}

}
