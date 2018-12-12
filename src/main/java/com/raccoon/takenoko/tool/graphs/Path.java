package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;

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

    public int graphLength(Graph g) {
        int res = 0;
        Tile[] stepList = new Tile[steps.size()];
        steps.toArray(stepList);
        Edge current;
        for (int i = 0; i < steps.size() - 1; i++) {
            current = g.getEdges().get(g.getEdges().indexOf(new Edge(stepList[i], stepList[i+1], 0)));  // dirty as hell but we need to get the actual edge to know its weight
            res += current.getWeight();
        }
        return res;
    }

    public Tile nextStep() {
        return steps.poll();
    }

    public int length() {
        return steps.size();
    }

    public boolean isEmpty() {
        return steps.isEmpty();
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


    /*
    ********************************************
    * *******************************************************
     */

    public Deque<Tile> getSteps() {
        return steps;
    }
}
