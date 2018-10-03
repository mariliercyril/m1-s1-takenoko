package com.raccoon.takenoko.game.objective;

/**
 * This interface provides methods for Objective (ParcelObjective, GardenerObjective, PandaObjective).
 */
public interface Objective {

	/**
	 * Gets if the objective is completed.
	 * 
	 * @return <b>true</b> if it is completed, <b>false</b> if not
	 */
	public boolean isCompleted();

    /**
     * Gets the score of the objective in question.
     * 
     * @return A positive score if the score is completed
     */
    public int getScore();

}
