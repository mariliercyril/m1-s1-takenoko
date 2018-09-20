package com.raccoon.takenoko.Player;

import com.raccoon.takenoko.Game.Game;

/**
 * Class representig the player taking part in the game. To be extended by a bot to
 * actually perform a move when it's its turn to play.
 * Will provide all the attributes and methods common to all players.
 */
public abstract class Player {
    private int score;

    public Player() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    /**
     * This method will be the one called by the game to give their turn to the players/
     * It will be calling the methods for the players to play their turn.
     * <p>
     * DESIGN PATTERN : TEMPLATE METHOD
     *
     * @param game the game in which the player is playing
     */
    public void play(Game game) {
        this.putDownTile(game);
        score++;
    }

    protected abstract void putDownTile(Game game);
}
