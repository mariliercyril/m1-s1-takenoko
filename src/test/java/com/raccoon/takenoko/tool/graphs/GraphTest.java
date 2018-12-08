package com.raccoon.takenoko.tool.graphs;

import com.raccoon.takenoko.game.tiles.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private Graph g;
    private Tile t1, t2, t3, t4;
    @BeforeEach
    public void init() {
        g = new Graph();
        t1 = new Tile();
        t2 = new Tile();
        t3 = new Tile();
        t4 = new Tile();

    }

    @Test
    void addEdge() {
        g.addEdge(t1, t2, 0);
        assertEquals(1, g.getEdges().size());
        assertTrue(g.getEdges(t1).contains(new Edge(t1, t2, 0)));
        assertTrue(g.getEdges(t2).contains(new Edge(t1, t2, 0)));
        g.addEdge(t3, t4, 2);
        assertEquals(2, g.getEdges().size());
        assertTrue(g.getEdges(t3).contains(new Edge(t3, t4,2)));
        assertTrue(g.getEdges(t4).contains(new Edge(t3,  t4, 2)));
        assertTrue(g.getEdges(t3).contains(new Edge(t4, t3, 2)));
        assertTrue(g.getEdges(t4).contains(new Edge(t4,t3, 2)));
    }


    @Test
    void removeEdge() {
        g.addEdge(t1, t2, 2);
        g.addEdge(t2, t3, 0);
        g.removeEdge(t1, t2);
        g.removeEdge(t2, t3);
        assertEquals(0, g.getEdges().size());
        assertFalse(g.getEdges(t1).contains(new Edge(t1, t2, 2)));
        assertFalse(g.getEdges(t2).contains(new Edge(t1, t2, 2)));
        assertFalse(g.getEdges(t1).contains(new Edge(t2, t1, 2)));
        assertFalse(g.getEdges(t2).contains(new Edge(t2, t1, 2)));
        assertFalse(g.getEdges(t3).contains(new Edge(t2, t3, 0)));

    }
}