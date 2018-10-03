package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;

import com.raccoon.takenoko.player.Player;

public abstract class AbstractObjective implements Objective {

	protected boolean isCompleted;
	protected int score;

	public AbstractObjective() {

		isCompleted = false;
		score = 0;
	}

	@Override
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

	@Override
	public int getScore() {

		return score;
	}

}
