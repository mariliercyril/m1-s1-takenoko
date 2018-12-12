package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private Graph graph;
    private Tile t1, t2, t3, t4;

    @BeforeEach
    void init() {
        graph = new Graph();
        t1 = new Tile();
        t2 = new Tile();
        t3 = new Tile();
        t4 = new Tile();

        t1.setPosition(new Point(0,0));
        t2.setPosition(new Point(0,1));
        t3.setPosition(new Point(1,0));
        t4.setPosition(new Point(1,1));

    }

    @Test
    void addEdge() {
        graph.addEdge(t1, t2, 0);
        assertEquals(1, graph.getEdges().size());
        assertTrue(graph.getEdges(t1).contains(new Edge(t1, t2, 0)));
        assertTrue(graph.getEdges(t2).contains(new Edge(t1, t2, 0)));
        graph.addEdge(t3, t4, 2);
        assertEquals(2, graph.getEdges().size());
        assertTrue(graph.getEdges(t3).contains(new Edge(t3, t4,2)));
        assertTrue(graph.getEdges(t4).contains(new Edge(t3,  t4, 2)));
        assertTrue(graph.getEdges(t3).contains(new Edge(t4, t3, 2)));
        assertTrue(graph.getEdges(t4).contains(new Edge(t4,t3, 2)));
    }


    @Test
    void removeEdge() {
        graph.addEdge(t1, t2, 2);
        graph.addEdge(t2, t3, 0);
        try {
            graph.removeEdge(t1, t2);
        } catch (UnexistingEdgeException e) {
            e.printStackTrace();
        }
        try {
            graph.removeEdge(t2, t3);
        } catch (UnexistingEdgeException e) {
            e.printStackTrace();
        }
        assertEquals(0, graph.getEdges().size());
        assertFalse(graph.getEdges(t1).contains(new Edge(t1, t2, 2)));
        assertFalse(graph.getEdges(t2).contains(new Edge(t1, t2, 2)));
        assertFalse(graph.getEdges(t1).contains(new Edge(t2, t1, 2)));
        assertFalse(graph.getEdges(t2).contains(new Edge(t2, t1, 2)));
        assertFalse(graph.getEdges(t3).contains(new Edge(t2, t3, 0)));
    }

    @Test
    void shortestPathTest() {

        graph.addEdge(t1, t2, 3);
        graph.addEdge(t1, t3, 1);
        graph.addEdge(t1, t4, 2);

        graph.addEdge(t2, t3, 1);
        graph.addEdge(t2, t4, 1);

        graph.addEdge(t3, t4, 2);

        Path sp1 = new Path(graph.shortestPath(t1)[0], t2);

        assertEquals(2, sp1.length(), "Path expected to need on intermediate step, doesn't have a length of 2");

        Tile t5 = new Tile();

        graph.addNode(t5);

        Path sp2 = new Path(graph.shortestPath(t1)[0], t5);

        assertEquals(0, sp2.length(), "Path between to non connexe nodes of a graph");
    }

    @Test
    void cleanTest() {

        graph.addEdge(t1, t2, 2);
        graph.addEdge(t1, t3, 1);
        graph.addEdge(t1, t4, 2);

        graph.addEdge(t2, t3, 1);
        graph.addEdge(t2, t4, 1);

        graph.addEdge(t3, t4, 2);

        graph.clean();

        assertEquals(4, graph.getEdges().size(), "Not the right number of edges after cleanup");

    }


}