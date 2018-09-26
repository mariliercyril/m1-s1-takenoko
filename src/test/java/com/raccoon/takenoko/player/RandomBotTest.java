package com.raccoon.takenoko.player;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.*;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.objective.ColorObjective;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RandomBotTest {
    private Player p;
    private Game g;

    @Mock
    ColorObjective mockObjective;

    @Mock
    RandomBot mockedBot;

    @Before
    public void build() {
        Takeyesntko.VERBOSE = false;

        g = new Game();
        p = new RandomBot();

        Tile greenTile0 = new BasicTile(Color.GREEN);
        Tile greenTile1 = new BasicTile(Color.GREEN);
        Tile pinkTile0 = new BasicTile(Color.PINK);

        g.getBoard().set(new Point(0,1), greenTile0);
        g.getBoard().set(new Point(1,1), pinkTile0);
        g.getBoard().set(new Point(1,2), greenTile1);

        when(mockObjective.isCompleted(any(), any())).thenReturn(true);
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

    @Test
    public void testWhereToPutGardener(){
        List<Point> accessiblePositions = g.getBoard().getAccessiblePositions(g.getGardener().getPosition());

        assertNotNull(p.whereToMoveGardener(accessiblePositions));
        assertTrue(accessiblePositions.contains(p.whereToMoveGardener(accessiblePositions)));
    }

    @Test
    public void failingPlannedActions(){
        when(mockedBot.planActions(any())).thenReturn(new Action[]{});
        Point beforePoint = g.getGardener().getPosition();
        List<Point> av = g.getBoard().getAvailablePositions();

        mockedBot.play(g);

        // for this test, we test that the player has had no impact on the board
        assertSame(beforePoint, g.getGardener().getPosition());
        assertSame(av, g.getBoard().getAvailablePositions());
    }
}
