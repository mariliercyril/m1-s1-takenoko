package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Tile value;
    private List<Node> neighbours;

    public Node() {
        neighbours = new ArrayList<>();
    }

    public Node(Tile value) {
        this();
        this.value = value;
    }

    public Tile getValue() {
        return value;
    }

    public void setValue(Tile value) {
        this.value = value;
    }

    public void addNeighbour(Node n) {
        neighbours.add(n);
    }
}
