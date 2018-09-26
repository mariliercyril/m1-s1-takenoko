package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;

/**
 * This interface provides methods for checking, among others, that an Objective is completed.
 */
public interface Objective {

	/**
	 * Check if the basic objective is completed.
	 */
    public boolean isCompleted(Tile basicTile, Board hashBoard);

    public int getScore();

}
