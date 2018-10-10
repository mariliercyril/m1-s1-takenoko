package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BambooBot extends Player {
    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
        List<Point> available = game.getBoard().getAvailablePositions();
        Map<Point, Integer> numberSameColorTiles = new HashMap<>();

        for (Point p : available) { // For each available spot
            numberSameColorTiles.put(p, 0);
            for (Tile adjacent : game.getBoard().getNeighbours(p)) {    // We find out how many tiles of the same color as the one we want to place are around it
                if (adjacent.getColor() == t.getColor() && adjacent.getBambooSize() < 4) {
                    numberSameColorTiles.put(p, numberSameColorTiles.get(p)+1);
                }
            }
        }

        Point bestChoice = available.get(0);
        for (Point p : available) {
            if (numberSameColorTiles.get(p) > numberSameColorTiles.get(bestChoice)) {
                bestChoice = p;
            }
        }

        return bestChoice;
    }
}
