package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.panda.TwoBambooChunksPandaObjective;
import com.raccoon.takenoko.tool.Constants;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import com.raccoon.takenoko.game.Color;

import java.util.*;
import java.util.List;

public abstract class BamBot extends Player {

    private Point getBestPoint(List<Point> available, Map<Point, Integer> numberSameColorTiles) {
        Point bestChoice = available.get(0);
        for (Point p : available) {
            if (numberSameColorTiles.get(p) > numberSameColorTiles.get(bestChoice)) {
                bestChoice = p;
            }
        }

        return bestChoice;
    }

    private Map<Point, Integer> getNumberSameColorTiles(Game game, Tile t) {
        List<Point> available = game.getBoard().getAvailablePositions();
        Map<Point, Integer> numberSameColorTiles = new HashMap<>();
        for (Point p : available) { // For each available spot
            numberSameColorTiles.put(p, numSameColorNeighbours(game, p));
        }
        return numberSameColorTiles;
    }


    @Override
    protected Point whereToPutDownTile(Game game, Tile t) { // Chooses where the best place to put down a tile
        Map<Point, Integer> numberSameColorTiles = getNumberSameColorTiles(game, t);
        return getBestPoint(game.getBoard().getAvailablePositions(), numberSameColorTiles);
    }

    @Override
    protected Tile chooseTile(Game game) {
        List<Tile> tiles = game.getTiles();
        Tile bestTile = tiles.get(0);
        Point bestPos = whereToPutDownTile(game, bestTile);
        int maxNumberSameColorTiles = getNumberSameColorTiles(game, bestTile).get(bestPos);

        Point currentPos;
        int currentNumberSameColorTiles;

        for (Tile t : tiles) {  // We find which of the three tiles grows the most bamboos when place at its best possible spot
            currentPos = whereToPutDownTile(game, t);
            currentNumberSameColorTiles = getNumberSameColorTiles(game, t).get(currentPos);

            if(currentNumberSameColorTiles > maxNumberSameColorTiles) {
                bestTile = t;
                maxNumberSameColorTiles = currentNumberSameColorTiles;
            }
        }

        for (Tile t : tiles) {  // We need to put back the tiles we don't want
            if (t != bestTile) {
                game.putBackTile(t);
            }
        }

        return bestTile;
    }

    private int numSameColorNeighbours(Game game, Point p) {
        int result = 0;
        Tile t = game.getBoard().get(p);
        for (Tile adjacent : game.getBoard().getNeighbours(p)) {    // We find out how many tiles of the same color as the one we want to place are around it
            if (adjacent.getColor() == t.getColor() && adjacent.getBambooSize() < 4 && adjacent.isIrrigated()) {
                result++;
            }
        }
        return result;
    }

    @Override
    protected Point whereToMoveGardener(Game game, List<Point> available) {

        Point bestPoint = available.get(0);
        int max = numSameColorNeighbours(game, bestPoint);

        for (Point p : available) {
            if (max < numSameColorNeighbours(game, p)) {
                max = numSameColorNeighbours(game, p);
                bestPoint = p;
            }
        }
        return bestPoint;
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
