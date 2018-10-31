package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;

import com.raccoon.takenoko.player.Player;

import java.util.Map;

/**
 * The {@code PandaObjective} class implements the <i>panda</i> {@link Objective}
 * which consist in "<b>having eaten expected numbers of bamboo chunks</b>".
 * <p>
 * The scores for the original patterns are:
 * <ul>
 * <li>3 for the {@code PandaObjective} which consists in
 * "<i>having eaten (at least) two GREEN bamboo chunks</i>"</li>
 * <li>4 for the {@code PandaObjective} which consists in
 * "<i>having eaten (at least) two YELLOW bamboo chunks</i>"</li>
 * <li>5 for the {@code PandaObjective} which consists in
 * "<i>having eaten (at least) two PINK bamboo chunks</i>"</li>
 * <li>6 for the {@code PandaObjective} which consists in
 * "<i>having eaten (at least) one bamboo chunk of each of the three colors (GREEN, YELLOW and PINK)</i>"</li>
 * </ul>
 */
public class PandaObjective extends Objective {

	private Pattern pattern;

	/**
	 * Constructs a {@code PandaObjective}.
	 * 
	 * @param pattern
	 * 	a panda pattern
	 */
	public PandaObjective(Pattern pattern) {

		super();

		this.pattern = pattern;
		score = this.pattern.score;
	}

	/**
	 * Gets the pattern of the current PandaObjective,
	 * i.e. the expected pattern for completing the PandaObjective in question.
	 * 
	 * @return the expected pattern
	 */
	public Pattern getPattern() {

		return pattern;
	}

	@Override
	public void checkIfCompleted(Player player) {

		// Gets the player's stomach to dissect it
		Map<Color, Integer> stomach = player.getStomach();

		// Gets the ID of the stomach by Gödelization of the content
		int stomachID = godelize(stomach.get(Color.GREEN), stomach.get(Color.YELLOW), stomach.get(Color.PINK));

		// PandaObjective, with a (panda) pattern (as a parameter), is completed
		// if the stomach ID is a multiple of the pattern ID.
		// (For example, if the stomach contains 0 GREEN bamboo chunk, 2 YELLOW bamboo chunks and 4 PINK bamboo chunks,
		// its ID is equal to 5625; if the pattern consists in having eaten (at least) two PINK bamboo chunks,
		// its ID is equal to 25: 5625 is a multiple of 25 (5625 / 25 = 225)..."
		isCompleted = (stomachID % pattern.id == 0);
	}

	/**
	 * A pattern of PandaObjective, such as the pattern of PandaObjective which consists in
	 * <i>having eaten (at least) two GREEN bamboo chunks</i>.
	 * <p>{@code Pattern} is an internal enum representing all the patterns of PandaObjective.
	 * Each of these patterns is defined by a ID, which is the number resulting of a Gödelization
	 * from the numbers of bamboo chunks, and the score when the PandaObjective is completed.</p>
	 */
	public enum Pattern {

		/**
		 * The singleton instance for the original pattern of PandaObjective which consists in
		 * <i>having eaten (at least) two GREEN bamboo chunks</i>.
		 */
		ORIGINAL_GREEN(godelize(2, 0, 0), 3),
		/**
		 * The singleton instance for the original pattern of PandaObjective which consists in
		 * <i>having eaten (at least) two YELLOW bamboo chunks</i>.
		 */
		ORIGINAL_YELLOW(godelize(0, 2, 0), 4),
		/**
		 * The singleton instance for the original pattern of PandaObjective which consists in
		 * <i>having eaten (at least) two PINK bamboo chunks</i>.
		 */
		ORIGINAL_PINK(godelize(0, 0, 2), 5),

		/**
		 * The singleton instance for the original pattern of PandaObjective which consists in
		 * <i>having eaten (at least) one bamboo chunk of each of the three colors (GREEN, YELLOW and PINK)</i>.
		 */
		ORIGINAL_THREE_COLORS(godelize(1, 1, 1), 6);

		private int id;
		private int score;

		private Pattern(int id, int score) {

			this.id = id;
			this.score = score;
		}

		/**
		 * Returns the ID of the pattern.
		 * 
		 * @return the ID of the pattern
		 */
		public int getID() {

			return id;
		}

	}

	/**
	 * Returns the number, <i>G</i>, resulting of the Gödelization which consists in doing the following calculation:
	 * let <i>Pn</i> be the <i>n</i>-th prime number,
	 * let <i>g</i> be the number of <b>green</b> bamboo chunks,
	 * <i>y</i> be the number of <b>yellow</b> bamboo chunks and
	 * <i>p</i> be the number of <b>pink</b> bamboo chunks;
	 * <i>G</i> = <i>P</i>1^<i>g</i> * <i>P</i>2^<i>y</i> * <i>P</i>3^<i>p</i>.
	 * 
	 * @param green
	 *  the number of GREEN bamboo chunks
	 * @param yellow
	 *  the number of YELLOW bamboo chunks
	 * @param pink
	 *  the number of PINK bamboo chunks
	 * 
	 * @return the ID
	 */
	private static int godelize(int green, int yellow, int pink) {

		return ((int)Math.pow(2, green) * (int)Math.pow(3, yellow) * (int)Math.pow(5, pink));
	}

}
