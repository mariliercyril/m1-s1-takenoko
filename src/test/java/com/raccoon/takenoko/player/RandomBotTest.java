package com.raccoon.takenoko.player;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.*;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.objective.parcel.AlignmentParcelObjective;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class RandomBotTest {
    private Player p;
    private Game g;

    @Mock
    AlignmentParcelObjective mockObjective;

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

        mockObjective.checkIfCompleted(any(), any());
        when(mockObjective.isCompleted()).thenReturn(true);
    }

    @Test
    public void testCreation() {
        assertEquals(p.getScore(), 0);
    }

    @Ignore("Objective completion not testable yet, TODO")
    @Test
    public void testObjectiveIncidenceOnScore() {
        try {
            p.play(g);
        } catch (ForbiddenActionException e) {
            fail("Player's turn threw an exception");
        }
        assertEquals(p.getScore(), 1);
    }

    @Test
    public void testPlayIncidenceOnBoard() {
        try {
            p.play(g);
        } catch (ForbiddenActionException e) {
            Assertions.assertNotNull(null, "Player's turn threw an exception.");
        }
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

        try {
        	
            mockedBot.play(g);
            fail("Expected an ForbiddenActionException to be thrown");
        } catch (Exception e) {
            assertEquals(ForbiddenActionException.class, e.getClass());
        }

        // for this test, we test that the player has had no impact on the board
        assertSame(beforePoint, g.getGardener().getPosition());
        assertSame(av, g.getBoard().getAvailablePositions());
    }

    @Test
    public void failingMovingGardener(){
        g.getBoard().set(new Point(1, 1), new BasicTile(Color.GREEN));
        g.getBoard().set(new Point(2, 1), new BasicTile(Color.GREEN));

        when(mockedBot.whereToMoveGardener(any())).thenReturn(new Point(2, 1));
        // planActions returns null if we don't add this line.
        when(mockedBot.planActions(any())).thenReturn(new Action[]{Action.MOVE_GARDENER, Action.VALID_OBJECTIVE});

        try {
            mockedBot.play(g);
            fail("Expected an ForbiddenActionException to be thrown");
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            assertEquals(ForbiddenActionException.class, e.getClass());
        }

    }
}
