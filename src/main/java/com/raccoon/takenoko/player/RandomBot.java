package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.objective.Objective;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.abs;

public class RandomBot extends Player {

    public RandomBot() {
        super();
    }

    /**
     * Puts down a tile at a random available location on the board given by the game
     *
     * @param game the game in which the player is playing
     */
    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
        Board b = game.getBoard();
        List availablePositions = b.getAvailablePositions();
        Collections.shuffle(availablePositions);

        Point playingPos;
        playingPos = (Point) availablePositions.get(0);
        return playingPos;
    }

    @Override
    protected Tile chooseTile(Game game) {  // Randomly chooses one tile out of three
        Random rand = new Random();
        ArrayList<Tile> tiles = game.getTiles();
        int choice = abs(rand.nextInt()) % tiles.size();
        for (int i = 0; i < tiles.size(); i++) {       // The players put the tiles he doesnt want back in the deck
            if (i != choice) {
                game.putBackTile(tiles.get(i));
            }
        }
        return tiles.get(choice);
    }

    @Override
    protected Point whereToMoveGardener(List<Point> available) {
        Collections.shuffle(available);
        return available.get(0);

    }

    @Override
    protected Point whereToMovePanda(List<Point> available) {
        Collections.shuffle(available);
        return available.get(0);
    }

    @Override
    protected Action[] planActions(Game game) {
        if(this.getObjectives().isEmpty()) {    // If we don't have any objective in our hand
            // we draw one
            return new Action[]{Action.DRAW_OBJECTIVE, Action.PUT_DOWN_TILE, Action.VALID_OBJECTIVE};
        }
        // Else we return a simple action set
        return new Action[]{Action.PUT_DOWN_TILE, Action.MOVE_GARDENER, Action.VALID_OBJECTIVE};
    }

    @Override
    protected Objective chooseObjectiveToValidate() {

        for (Objective objective : this.getObjectives()) {  // We go through all the objectives

            if (objective.isCompleted()) {   // If we find one completed,
                return objective;           // we return it
            }

        }

        return null;    // If no objective is completed, we just return null

    }
}
