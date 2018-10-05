package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.player.Player;

/**
 * This abstract class allows to define the Objective classes (ParcelObjective, GardenerObjective, PandaObjective).
 */
public abstract class Objective {

	private static final String RESULT_FORMAT = "%s{isCompleted=%B, score=%d, color=%S}";

	protected boolean isCompleted;
	protected int score;
	protected Color color;

    public Objective() {

        isCompleted = false;
    }

    /**
     * Gets if the objective is completed.
     *
     * @return <b>true</b> if it is completed, <b>false</b> if not
     */
    public boolean isCompleted() {

        return isCompleted;
    }

    /**
     * Checks if a Parcel Objective is completed.
     *
     * @param basicTile The tile from which we check
     * @param hashBoard The game board on which we look for the pattern
     */
    public void checkIfCompleted(Tile basicTile, Board hashBoard) {

        throw new UnsupportedOperationException();
    }

    /**
     * Checks if a Panda Objective is completed.
     *
     * @param player The player in which to look for the bamboo chunks
     */
    public void checkIfCompleted(Player player) {

        throw new UnsupportedOperationException();
    }

    /**
     * Gets the score of the objective in question.
     *
     * @return A positive score if the score is completed
     */
    public int getScore() {

        return score;
    }

    /**
     * Gets the color of the objective.
     * 
     * @return The color of the objective
     */
    public Color getColor() {

        return color;
    }

    /**
     * Redefines the method "toString()".
     * 
     * @return A {@code String} with values of the objective
     */
    public String toString() {

        return  String.format(RESULT_FORMAT, (this.getClass()).getSimpleName(), isCompleted, score, color);
    }

}
