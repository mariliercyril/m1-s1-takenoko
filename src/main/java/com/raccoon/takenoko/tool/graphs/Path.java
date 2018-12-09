package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

import java.awt.Point;
import java.util.*;

public class Path {
    private Queue<Tile> steps;
    private Map<Color, Integer> bamboos;

    public Path() {
        steps = new ArrayDeque<>();
    }

    public void addStep(Tile t) {
        steps.add(t);
    }

    public Tile nextStep() {
        return steps.poll();
    }

    public int length() {
        return steps.size();
    }

    public Map<Color, Integer> getBambooYield() {
        Map<Color, Integer> yield = new EnumMap<>(Color.class);
        for (Color c : Color.values()) {
            yield.put(c, 0);
        }

        for (Tile t : steps) {
            if (t.getBambooSize() > 0) {
                yield.put(t.getColor(), yield.get(t.getColor())+1);
            }
        }
        return yield;
    }
}
