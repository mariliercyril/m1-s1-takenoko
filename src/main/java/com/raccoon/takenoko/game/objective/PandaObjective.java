package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;

import com.raccoon.takenoko.player.Player;

import java.util.Map;

/**
 * The {@code PandaObjective} class implements the <i>panda</i> {@link Objective}
 * which consists in "<b>having eaten at least two bamboo chunks of which the color is an expected color</b>"
 * or in "<b>having eaten at least one bamboo chunk of each of the color</b> (GREEN, YELLOW, PINK)".
 * <p>
 * The score base, for the first Objective type, is equal to 3; consequently, the scores are:
 * <ul>
 * <li>3 for (at least) two GREEN bamboo chunks</li>
 * <li>4 for (at least) two YELLOW bamboo chunks</li>
 * <li>5 for (at least) two PINK bamboo chunks</li>
 * </ul>
 */
public class PandaObjective extends Objective {

	private static final int SCORE_BASE = 3;

	/**
	 * Constructs a {@code PandaObjective} of the first type
	 * (i.e. a {@code PandaObjective} <i>with a specified color</i>).
	 * 
	 * @param color
	 *  the color of the bamboo chunks which should have been eaten
	 */
	public PandaObjective(Color color) {

		super();
		score = SCORE_BASE + ((color == null) ? SCORE_BASE : color.ordinal());
		this.color = color;
	}

	@Override
	public void checkIfCompleted(Player player) {

		// Gets the player's stomach to dissect it
		Map<Color, Integer> stomach = player.getStomach();

		// Is completed if the stomach in question contains at least 2 bamboo chunks
		// with the expected color (color of Objective)
		// or if it contains at least 1 bamboo chunk per color
		if (stomach.get(color) > 1) {
			isCompleted = true;
		} else if (stomach.values().stream().allMatch(n -> n > 0)) {
			isCompleted = true;
		}
	}

}
