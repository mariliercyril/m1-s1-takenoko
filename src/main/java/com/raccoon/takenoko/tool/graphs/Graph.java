package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Tile, List<Edge>> adjacency;
    private List<Edge> edges;

    public Graph() {
        this.adjacency = new HashMap<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(Tile t) {
        if (!adjacency.containsKey(t)) {
            adjacency.put(t, new ArrayList<>());
        }
    }

    public void addEdge(Tile t, Tile n, int weight) {
        addNode(t); // for safety
        addNode(n);

        Edge newEdge = new Edge(t, n, weight);
        if (!adjacency.get(t).contains(newEdge)) {
            adjacency.get(t).add(newEdge);
            adjacency.get(n).add(newEdge);
            edges.add(newEdge)
        }

    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void removeEdge(Tile a, Tile b) {
        Edge toRmv = new Edge(a, b, 0);
        edges.remove(toRmv);
        adjacency.get(a).remove(toRmv);
        adjacency.get(b).remove(toRmv);
    }
}
