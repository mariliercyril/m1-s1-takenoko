package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.tool.Constants;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
