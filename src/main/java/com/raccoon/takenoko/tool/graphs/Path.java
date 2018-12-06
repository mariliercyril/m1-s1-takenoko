package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

import java.awt.Point;
import java.util.*;

public class Path {
    private Queue<Point> steps;
    private Map<Color, Integer> bamboos;

    public Path() {
        steps = new ArrayDeque<>();
        bamboos = new EnumMap<>(Color.class);
        for (Color c : Color.values()) {
            bamboos.put(c, 0);
        }
    }

    public void addStep(Tile t) {
        if (Objects.nonNull(t.getColor()) && t.getBambooSize() > 0) {
            bamboos.put(t.getColor(), bamboos.get(t.getColor()) + 1);
        }
        steps.add(t.getPosition());
    }

    public Point nextStep() {
        return steps.poll();
    }

    public int length() {
        return steps.size();
    }
}
