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
        this.addEdge(newEdge);

    }

    public void addEdge(Edge edge) {
        if (!adjacency.get(edge.getLeft()).contains(edge)) {
            adjacency.get(edge.getLeft()).add(edge);
            adjacency.get(edge.getRight()).add(edge);
            edges.add(edge);
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

    public void removeEdge(Edge e) {
        removeEdge(e.getLeft(), e.getRight());
    }
}
