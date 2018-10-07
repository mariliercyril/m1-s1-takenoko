package com.raccoon.takenoko.game.objective.panda;

import com.raccoon.takenoko.game.Color;

import com.raccoon.takenoko.game.objective.Objective;

import com.raccoon.takenoko.player.Player;

import java.util.HashMap;

/**
 * The {@code TwoBambooChunksPandaObjective} class implements the <i>panda</i> {@link Objective}
 * which consists in "<b>having eaten (at least) two bamboo chunks of which the color is an expected color</b>".
 * <p>
 * The score base is equal to 3; consequently, the scores are:
 * <ul>
 * <li>3 for GREEN bamboo chunks</li>
 * <li>4 for YELLOW bamboo chunks</li>
 * <li>5 for PINK bamboo chunks</li>
 * </ul>
 */
public class TwoBambooChunksPandaObjective extends Objective {

	private static final int SCORE_BASE = 3;

	/**
	 * Constructs a {@code TwoBambooChunksPandaObjective} with a specified color.
	 * 
	 * @param color
	 *  the color of the bamboo chunks which should have been eaten
	 */
	public TwoBambooChunksPandaObjective(Color color) {

		super();
		this.color = color;
		setScore(color);
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

	/**
	 * Sets the score according to the color.
	 * 
	 * @param color
	 *  the color which is associated with the objective
	 */
	private void setScore(Color color) {

		score = SCORE_BASE + color.ordinal();
	}

}
