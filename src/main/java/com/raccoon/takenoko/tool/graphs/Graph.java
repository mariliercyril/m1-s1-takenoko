package com.raccoon.takenoko.tool.graphs;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node n) {
        nodes.add(n);
    }

    public void connectNodes(Node n1, Node n2) {
        if (n1 != n2) {
            n1.addNeighbour(n2);
            n2.addNeighbour(n1);
        }
    }
}
