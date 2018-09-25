package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.objective.BasicObjective;
import com.raccoon.takenoko.game.objective.ColorObjective;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.Takeyesntko;

/**
 * Class representig the player taking part in the game. To be extended by a bot to
 * actually perform a move when it's its turn to play.
 * Will provide all the attributes and methods common to all players.
 */
public abstract class Player {
    private int score;
    private int id;
    private static int counter = 0;

    public Player() {
        score = 0;
        counter++;
        id = counter;
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
        Tile t = this.chooseTile(game);

        // player puts down a tile according to its algorithm
        this.putDownTile(game, t);

        // check for objective completion
        Objective objective = new ColorObjective();

        if(objective.isCompleted(t, game.getBoard())){
            Takeyesntko.print("Player has completed an objective ! 1 point to the player !");
            score++;
        }

        Takeyesntko.print("Player has played. Current score : " + getScore());

    }

    protected abstract void putDownTile(Game game, Tile t);
    protected abstract Tile chooseTile(Game game);

    public int getId() {
        return id;
    }
}
