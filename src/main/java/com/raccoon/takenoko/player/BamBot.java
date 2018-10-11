package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.panda.TwoBambooChunksPandaObjective;
import com.raccoon.takenoko.tool.Constants;

import java.awt.*;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.tool.Tools;

import java.util.*;
import java.util.List;

public class BamBot extends RandomBot {


    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
       Map<Point, Integer> possibleBambooGrowth = new HashMap<>();

       for (Point available : game.getBoard().getAvailablePositions()) {    // Checking the possible outcomes of placing the tile
           possibleBambooGrowth.put(available, 0);
           for (Tile adjacent : game.getBoard().getNeighbours(available)) {
                if (t.getColor() == adjacent.getColor() && adjacent.isIrrigated()) {
                    possibleBambooGrowth.put(available, possibleBambooGrowth.get(available)+1);
                }
           }
       }

        return Tools.mapMaxKey(possibleBambooGrowth);
    }


    @Override
    protected Point whereToMovePanda(Game game, List<Point> available) {
        //  The player choses to eat the bamboo pieces he lacks the most
        int minValue = getStomach().get(Color.PINK);
        Color minColor = Color.PINK;

        for (Color c : Color.values()) {
            if (getStomach().get(c) < minValue) {
                minValue = getStomach().get(c);
                minColor = c;
            }
        }

        //  Looks for the first tile of the needed color

        for (Point p : available) {
            if (game.getBoard().get(p).getColor() == minColor) {
                return p;
            }
        }

        return available.get(0);
    }

    @Override
    protected Action[] planActions(Game game) {
        Action[] actionSet = new Action[]{Action.PUT_DOWN_TILE, Action.MOVE_GARDENER, Action.VALID_OBJECTIVE};  // Safety action set

        if (game.getBoard().getAllTiles().size() == 1) {
            return actionSet;
        }

        boolean hasWantedObjective = false;

        // Always wants to have one "have x bamboos of each color" objective
        for (Objective obj : getObjectives()) {
            if (obj instanceof TwoBambooChunksPandaObjective) {
                hasWantedObjective = true;
                break;
            }
        }
        if (!hasWantedObjective && getObjectives().size() < Constants.MAX_AMOUNT_OF_OBJECTIVES) {   //  Rule to pick a new objective
            actionSet[0] = Action.DRAW_OBJECTIVE;
        }

        if (isMovingPandaUseful(game) && needToEatBamboo()) { // Rule to move the panda
            actionSet[1] = Action.MOVE_PANDA;
        }

        return actionSet;
    }

    private boolean isMovingPandaUseful(Game game) {    //  Tells if there is any bamboo to be eaten
        for (Point p : game.getBoard().getAccessiblePositions(game.getPanda().getPosition())) {
            if (game.getBoard().get(p).getBambooSize() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean needToEatBamboo() { //  The player wants to always have three bamboo of each color
        for (Color c : Color.values()) {
            if (getStomach().get(c) < 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Objective chooseObjectiveToValidate() {   // The player validates the bamboo objectives first
        // For now, he doesn't try to validate the highest-scoring objective
        List<Objective> completedObjectives = new ArrayList<>();
        for (Objective obj : getObjectives()) {
            if (obj.isCompleted()) {
                completedObjectives.add(obj);
            }
         }

         if (completedObjectives.size() != 0) {
             for (Objective completed : completedObjectives) {
                 if (completed instanceof TwoBambooChunksPandaObjective) {
                     return completed;
                 }
             }
             return completedObjectives.get(0);
         }

        return null;
    }
}
