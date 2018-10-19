package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class GardenerObjectiveTest {

    private Board b;
    private Objective go1;

    @Before
    public void setup() {
        Game g = new Game();
        b = g.getBoard();

        b.set(new Point(1, 1), new Tile(Color.PINK));
        b.set(new Point(0, 1), new Tile(Color.YELLOW));
        b.set(new Point(1, 0), new Tile(Color.GREEN));

        go1 = new GardenerObjective(3, Color.GREEN);
    }

    @Test
    public void isntCompletedIfNoBambooGrew() {
        go1.checkIfCompleted(b);
        assertFalse("Gardener objective is completed even if number of chunks is wrong", go1.isCompleted());
    }

    @Test
    public void isCompletedIfThereAreEnoughBamboos() {
        Tile t = b.get(new Point(1, 0));
        t.increaseBambooSize(1);
        t.increaseBambooSize(1);
        go1.checkIfCompleted(b);
        assertTrue("Gardener objective isn't completed even though there are enough bamboos of the right color on the board", go1.isCompleted());

    }
}
