package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;

import com.raccoon.takenoko.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
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

	private List<Color> colors;
	private Map<Color, Integer> pattern;

	/**
	 * Constructs a {@code PandaObjective}.
	 * 
	 * @param color
	 *  color (as a Color array) of the bamboo chunks which should have been eaten
	 */
	public PandaObjective(Color... colors) {

		super();
		this.colors = new ArrayList<>(Arrays.asList(colors));
		pattern = new EnumMap<>(Color.class);
		score();
	}

	@Override
	public void checkIfCompleted(Player player) {

		// Gets the player's stomach to dissect it
		Map<Color, Integer> stomach = player.getStomach();

		// PandaObjective with one color (as a parameter) is completed
		// if the stomach contains at least 2 bamboo chunks of the color in question
		if ((colors.size() == 1) && (stomach.get(colors.get(0)) >= 2)) {
			isCompleted = true;
		}
		// PandaObjective with the three colors (as parameters) is completed
		// if the stomach contains at least 1 bamboo chunk per color
		if ((colors.size() == 3) && (stomach.values().stream().allMatch(n -> n >= 1))) {
			isCompleted = true;
		}
	}

	/**
	 * Gets the expected pattern for completing the PandaObjective in question.
	 * 
	 * @return pattern
	 * 	the expected pattern depending on colors
	 */
	public Map<Color, Integer> getPatternForCompleting() {

		if (colors.size() == 1) {
			pattern.put(colors.get(0), 2);
		}
		if (colors.size() == 3) {
			for (Color color : colors) {
				pattern.put(color, 1);
			}
		}

		return pattern;
	}

	/**
	 * Assigns the value to score according to the {@code PandaObjective} case.
	 */
	private void score() {

		if (colors.size() == 1) {
			switch (colors.get(0)) {
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
		if (colors.size() == 3) {
			score = 6;
		}
	}

	// WARNING: Maybe, this method would replace the method getColor().
	public List<Color> getColors() {

		return colors;
	}

}
