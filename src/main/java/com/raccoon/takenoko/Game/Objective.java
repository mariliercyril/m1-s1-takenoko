package com.raccoon.takenoko.Game;

/**
 * This interface provides methods for checking, among others, that an Objective is satisfied.
 */
public interface Objective {

    public boolean checkIfCompleted();
    public int getScore();
}
