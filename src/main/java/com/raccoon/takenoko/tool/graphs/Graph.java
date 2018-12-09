package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;

import java.awt.*;
import java.util.*;
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

    public List<Edge> getEdges(Tile t) {
        return adjacency.get(t);
    }

    public Path shortestPath(Tile start, Tile end) {
        // Dijkstra algorithm implementation


        Map<Tile, Tile> trace = new HashMap<>();
        Map<Tile, Integer> distance = new HashMap<>();

        for (Tile tile : this.adjacency.keySet()) {
            distance.put(tile, 230);
        }

        distance.put(start, 0);
        trace.put(start, null);

        Comparator<Tile> comparator = new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                return distance.get(o1) - distance.get(o2);
            }
        };

        PriorityQueue<Tile> pointsToVisit = new PriorityQueue<>(this.adjacency.keySet().size(), comparator);

        for (Tile tile : this.adjacency.keySet()) {
            pointsToVisit.offer(tile);
        }

        while(! pointsToVisit.isEmpty()) {
            Tile current = pointsToVisit.poll();
            for (Edge edge : this.adjacency.get(current)) {
                Tile neighbour = edge.otherEnd(current);
                int distanceUsingCurrent = distance.get(current) + edge.getWeight();
                if (distance.get(neighbour) > distanceUsingCurrent) {
                    trace.put(neighbour, current);
                    distance.put(neighbour, distanceUsingCurrent);
                }
            }
        }
        // If we didn't return anything we are in the case of a non connex component
        // shouldn't happen in a board



        return new Path(trace, end);
    }
}
