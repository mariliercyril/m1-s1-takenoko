package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.BasicObjective;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.Takeyesntko;

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
        Tile t = game.getTiles()[0];
        this.putDownTile(game, t);

        Takeyesntko.print("Player's current score : " + getScore());
        BasicObjective objective = new BasicObjective();

        if(objective.checkIfCompleted(t, game.getBoard())){
            Takeyesntko.print("Player has completed an objective !");
        }

        Takeyesntko.print("Player has played, score is increasing (no score computation yet)");
        score++;
    }

    protected abstract void putDownTile(Game game, Tile t);
}
