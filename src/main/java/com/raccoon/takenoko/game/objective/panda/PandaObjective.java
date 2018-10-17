package com.raccoon.takenoko.game.objective.panda;

import com.raccoon.takenoko.game.Color;

import com.raccoon.takenoko.game.objective.Objective;

import com.raccoon.takenoko.player.Player;

import java.util.HashMap;

/**
 * The {@code PandaObjective} class implements the <i>panda</i> {@link Objective}
 * which consists in "<b>having eaten (at least) two bamboo chunks of which the color is an expected color</b>"
 * or in "<b>having eaten three bamboo chunks of which the color is different from other</b>".
 * <p>
 * The score base, for the first Objective type, is equal to 3; consequently, the scores are:
 * <ul>
 * <li>3 for GREEN bamboo chunks</li>
 * <li>4 for YELLOW bamboo chunks</li>
 * <li>5 for PINK bamboo chunks</li>
 * </ul>
 */
public class PandaObjective extends Objective {

	private static final int SCORE_BASE = 3;

	/**
	 * Constructs a {@code TwoBambooChunksPandaObjective} with a specified color.
	 * 
	 * @param color
	 *  the color of the bamboo chunks which should have been eaten
	 */
	public PandaObjective(Color color) {

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
		// Or if the size of the bamboo which is on the final tile is at least equal to 1
		// and each tile in question has a color different from other
		if (stomach.entrySet().stream().anyMatch(b -> ((b.getKey()).equals(color) && (b.getValue()) >= 2))) {
			isCompleted = true;
		} else {
			stomach.entrySet().stream().forEach(b1 -> {
				if ((b1.getValue()) >= 1) {
					stomach.entrySet().stream().forEach(b2 -> {
						if ((b2.getValue()) >= 1) {
							stomach.entrySet().stream().filter(b3 -> ((b3.getValue()) >= 1)).forEach(b3 -> {
								if (!(b2.getKey()).equals(b1.getKey()) && !(b3.getKey()).equals(b1.getKey()) && !(b3.getKey()).equals(b2.getKey())) {
									isCompleted = true;
									score = SCORE_BASE + SCORE_BASE;
								}
							});
						}
					});
				}
			});
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
