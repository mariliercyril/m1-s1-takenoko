package com.raccoon.takenoko.player;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.ColorObjective;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.awt.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RandomBotTest {
    private Player p;
    private Game g;

    @Mock
    ColorObjective mockObjective;



    @Before
    public void build() {
        Takeyesntko.VERBOSE = false;

        g = new Game();
        p = new RandomBot();

        when(mockObjective.checkIfCompleted(any(), any())).thenReturn(true);
    }

    @Test
    public void testCreation() {
        assertEquals(p.getScore(), 0);
    }

    @Ignore("Objective completion not testable yet, TODO")
    @Test
    public void testObjectiveIncidenceOnScore() {
        p.play(g);
        assertEquals(p.getScore(), 1);
    }

    @Test
    public void testPlayIncidenceOnBoard() {
        p.play(g);
        // we test that the starting tile has at least one neighbour
        assertTrue(g.getBoard().getNeighbours(new Point(0, 0)).size() > 0);
    }
}
