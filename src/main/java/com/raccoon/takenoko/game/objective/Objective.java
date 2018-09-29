package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;

/**
 * This interface provides methods for checking, among others, that an Objective is completed.
 */
public interface Objective {

	/**
	 * Gets if the basic objective is completed.
	 * 
	 * @return <b>true</b> if it is completed, <b>false</b> if not
	 */
	public boolean isCompleted();

	/**
	 * Checks if the basic objective is completed.
	 */
    public void checkIfCompleted(Tile tile, Board board);

    public int getScore();

}
