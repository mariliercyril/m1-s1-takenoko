package com.raccoon.takenoko.tool.graphs;

import ch.qos.logback.core.pattern.color.YellowCompositeConverter;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {
    Tile t1;
    Tile t2;
    Tile t3;
    Tile t4;
    Path p;

    @BeforeEach
    void init() {
        p = new Path();
        t1 = new Tile(Color.GREEN);
        t2 = new Tile(Color.YELLOW);
        t3 = new Tile(Color.PINK);
        t4 = new Tile(Color.GREEN);
    }

    @Test
    void addStep() {
        p.addStep(t1);
        p.addStep(t2);
        assertEquals(2, p.length());
    }

    @Test
    void nextStep() {
        p.addStep(t3);
        p.addStep(t1);
        p.addStep(t2);
        assertEquals(t3, p.nextStep());
        assertEquals(2, p.length());
    }

    @Test
    void getBambooYield() {
        Map<Color, Integer> yield;
        t1.setBambooSize(2);    // green
        t2.setBambooSize(1);    // yellow
        t3.setBambooSize(0);    // pink
        t4.setBambooSize(1);    // green

        yield = p.getBambooYield();
        for (Color c : yield.keySet()) {
            assertEquals(0, (int) yield.get(Color.GREEN));
        }

        p.addStep(t1);
        p.addStep(t2);
        p.addStep(t3);
        p.addStep(t4);

        yield = p.getBambooYield();
        assertEquals(2, (int)yield.get(Color.GREEN));
        assertEquals(1, (int)yield.get(Color.YELLOW));
        assertEquals(0, (int)yield.get(Color.PINK));

        t3.setBambooSize(2);
        yield = p.getBambooYield();
        assertEquals(1, (int)yield.get(Color.PINK));
    }
}