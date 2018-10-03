package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.player.Player;

/**
 * This abstract class allows to define the Objective classes (ParcelObjective, GardenerObjective, PandaObjective).
 */
public abstract class Objective {

	protected boolean isCompleted;
	protected int score;

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
	 * @param basicTile
	 * @param hashBoard
	 */
	public abstract void checkIfCompleted(Tile basicTile, Board hashBoard) throws UnsupportedOperationException;

	/**
	 * Checks if a Panda Objective is completed.
	 * 
	 * @param player
	 */
	public abstract void checkIfCompleted(Player player) throws UnsupportedOperationException;

    /**
     * Gets the score of the objective in question.
     * 
     * @return A positive score if the score is completed
     */
	public int getScore() {

		return score;
	}

}
