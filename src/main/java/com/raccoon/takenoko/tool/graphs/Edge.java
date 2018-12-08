package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Edge {
    private Tile left;
    private Tile right;
    private int weight;

    public Edge(Tile a, Tile b, int weight) {
        this.left = a;
        this.right = b;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            Edge b = (Edge)o;
            return (this.left.equals(b.left) && b.right.equals(this.right)) ||
                    (this.left.equals(b.right) && b.left.equals(this.right));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Tile getLeft() {
        return left;
    }

    public Tile getRight() {
        return right;
    }
}
