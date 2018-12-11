package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
;
import java.util.*;

public class Path {
    private Deque<Tile> steps;
    private Map<Color, Integer> bamboos;

    public Path() {
        steps = new ArrayDeque<>();
    }

    public Path(Map<Tile, Tile> trace, Tile arrival) {

        this();

        Tile current = arrival;

        // What happens when using get method on a key that doesn't exist ?
        while (/*trace.containsKey(current) &&*/ trace.get(current) != null) {
            steps.addFirst(current);
            current = trace.get(current);
        }
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
