package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Comparator<Tile> comparator = (Tile o1, Tile o2) -> distance.get(o1) - distance.get(o2);

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

        return new Path(trace, end);
    }

    public void clean() {

        Comparator<Edge> edgeComparator = (Edge o1, Edge o2) -> o2.getWeight() - o1.getWeight();

        PriorityQueue<Edge> edges = new PriorityQueue<>(500, edgeComparator);

        edges.addAll(this.getEdges().stream().filter(e -> e.getWeight() > 1).collect(Collectors.toList()));


        while (! edges.isEmpty()) { // While we have edges to treat
            Edge edge = edges.poll();
            this.removeEdge(edge);
            Path shortestPath = this.shortestPath(edge.getLeft(), edge.getRight());

            int pathLength = shortestPath.length();
            /* If, when we remove an edge, the two ends of the edge are not connected anymore
            or the path to go from one to the other is longer  */
            if (pathLength <= 0 || pathLength > edge.getWeight()) {
                // We put it back
                this.addEdge(edge);
            }
            // Else we keep it out : no need for it in the graph, and it could lead to avoid eating bamboos
        }
    }
}
