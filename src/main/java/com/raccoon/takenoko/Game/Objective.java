package com.raccoon.takenoko.Game;

/**
 * This interface provides methods for checking, among others, that an Objective is completed.
 */
public interface Objective {

	// TODO: To rename the method "checkIfCompleted" into "isCompleted".
    public boolean checkIfCompleted(Tile basicTile, Board hashBoard);

    public int getScore();
}
