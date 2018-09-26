package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.Takeyesntko;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

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

    public int getId() {
        return id;
    }

    /**
     * This method will be the one called by the game to give their turn to the players/
     * It will be calling the methods for the players to play their turn.
     * <p>
     * DESIGN PATTERN : TEMPLATE METHOD
     *
     * @param game the game in which the player is playing
     */
    public final void play(Game game) {
        /*
        Tile t = this.chooseTile(game);

        // player puts down a tile according to its algorithm
        this.putDownTile(game, t);

        // check for objective completion
        Objective objective = new ColorObjective();

        if(objective.checkIfCompleted(t, game.getBoard())){
            Takeyesntko.print("Player has completed an objective ! 1 point to the player !");
            score++;
        }

        Takeyesntko.print("Player has played. Current score : " + getScore());

        */

        // 1st step : ask bot to plan actions
        Action[] plannedActions = planActions(game);

        // check if the actions are compatible (exactly 2 costly actions)
        int validityCheck = 0;
        for (int i = 0; i < plannedActions.length; validityCheck += plannedActions[i++].getCost()) ;
        if (validityCheck != 2) {
            Takeyesntko.print("Player can't play these actions. Player tried to cheat. Player's turn is cancelled.");
            return;
        }
        Takeyesntko.print("Choosen actions : " + Arrays.toString(plannedActions));

        // step 2 : execute all actions
        for (Action a : plannedActions) {
            execute(a, game);
        }


        Takeyesntko.print("Player has played. Current score : " + getScore());

    }

    /**
     * BOT CAN'T ACCESS THIS METHOD
     * Used to enforce a honest behavior
     * @param a action to play
     * @param game current game
     */
    private void execute(Action a, Game game) {
        Takeyesntko.print("PLAYING " + a);
        switch (a) {
            case PUT_DOWN_TILE:
                Tile t = this.chooseTile(game);
                Point choice = this.whereToPutDownTile(game, t);
                t.setPosition(choice);
                this.putDownTile(game, t);
                break;
            case MOVE_GARDENER:
                Point whereToMove = whereToMoveGardener(game.getBoard().getAccessiblePositions(game.getGardener().getPosition()));
                // check that point is in available points array
                game.getGardener().move(game.getBoard(), whereToMove);
                break;
            case VALID_OBJECTIVE:
            default:
                Takeyesntko.print(a + " UNSUPPORTED");
        }
    }

    /**
     * BOT CAN'T ACCESS THIS METHOD
     * Used by the player to actually put down the tile the player has chosen to put down
     * @param game current game
     * @param t tile to put down
     */
    private void putDownTile(Game game, Tile t) {
        game.getBoard().set(t.getPosition(), t);
    }

    protected abstract Action[] planActions(Game game);

    protected abstract Point whereToPutDownTile(Game game, Tile t);

    protected abstract Tile chooseTile(Game game);

    protected abstract Point whereToMoveGardener(List<Point> available);
}
