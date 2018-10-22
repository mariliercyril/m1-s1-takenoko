package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;

import com.raccoon.takenoko.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The {@code PandaObjective} class implements the <i>panda</i> {@link Objective}
 * which consist in "<b>having eaten at least two bamboo chunks of one expected color</b>"
 * or in "<b>having eaten at least one bamboo chunk of each of the three colors</b> (GREEN, YELLOW and PINK)".
 * <p>
 * The scores are:
 * <ul>
 * <li>3 for the {@code PandaObjective} which consists in "<i>having eaten (at least) two GREEN bamboo chunks</i>"</li>
 * <li>4 for the {@code PandaObjective} which consists in "<i>having eaten (at least) two YELLOW bamboo chunks</i>"</li>
 * <li>5 for the {@code PandaObjective} which consists in "<i>having eaten (at least) two PINK bamboo chunks</i>"</li>
 * <li>6 for the {@code PandaObjective} which consists in "<i>having eaten (at least) one bamboo chunk of each of the three colors</i>"</li>
 * </ul>
 */
public class PandaObjective extends Objective {

	private List<Color> color = new ArrayList<>();

	/**
	 * Constructs a {@code PandaObjective}.
	 * 
	 * @param color
	 *  color (as a Color array) of the bamboo chunks which should have been eaten
	 */
	public PandaObjective(Color... color) {

		super();
		score();
		this.color = Arrays.asList(color);
	}

	@Override
	public void checkIfCompleted(Player player) {

		// Gets the player's stomach to dissect it
		Map<Color, Integer> stomach = player.getStomach();

		switch (color.size()) {
			// PandaObjective with one color (as a parameter) is completed
			// if the stomach contains at least 2 bamboo chunks of the color in question
			case 1:
				if (stomach.get(color.get(0)) >= 2) {
					isCompleted = true;
				}
				break;
			// PandaObjective with the three colors (as parameters) is completed
			// if the stomach contains at least 1 bamboo chunk per color
			case 3:
				if (stomach.values().stream().allMatch(n -> n >= 1)) {
					isCompleted = true;
				}
				break;
		}
	}

	/**
	 * Assigns the value to score according to the {@code PandaObjective} case.
	 */
	private void score() {

		switch (color.size()) {
			case 1:
				switch (color.get(0)) {
					case GREEN:
						score = 3;
						break;
					case YELLOW:
						score = 4;
						break;
					case PINK:
						score = 5;
						break;
				}
			case 3:
				score = 6;
				break;
		}
	}

}
