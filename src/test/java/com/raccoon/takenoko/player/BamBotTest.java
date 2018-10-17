package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.tool.UnitVector;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class BamBotTest {

    private Map<Point, Color> tileColors;
    private Game g;
    private Player bot;
    // I don't want to write Color.GREEN every time I need it (which is a lot)
    private final Color green = Color.GREEN;
    private final Color pink = Color.PINK;
    private final Color yellow = Color.YELLOW;

    private void place(int x, int y, Color c) {
        g.getBoard().set(new Point(x,y), new Tile(c));
    }

    private void irrigate(int x, int y, UnitVector v) {
        g.getBoard().irrigate(new Point(x,y), v.getVector());
    }

    @Before
    public void init() {
        g = new Game(1);
        bot = g.getPlayers().get(0);
    }

    @Test
    public void whereToPutDownTile() {
    }

    @Test
    public void chooseTile() {
    }

    @Test
    public void whereToMoveGardener() {
        // First ring
        place(0,1, green);
        place(1,1, yellow);
        place(1,0, green);
        place(0,-1,yellow);
        place(-1, -1, pink);
        place(-1,0, green);

        // Half of the second ring
        place(-1, 1, green);
        place(1,2, pink);
        place(2, 1, yellow);
        place(1, -1, pink);
        place(-1, -2, pink);
        place(-2, -1, green);

        // Second half of the second ring
        place(0, 2, pink);
        place(2, 2, green);
        place(2,0, yellow);
        place(0, -2, yellow);
        place(-2, -2, pink);
        place(-2, 0, green);

        // Irrigating a few tiles to make a single cluster of growable tiles
        irrigate(-1, -1, UnitVector.I);
        irrigate(-1, -1, UnitVector.N);
        irrigate(-1, -1, UnitVector.M);

        List<Point> possibleBest = new ArrayList<>();   // These two spots are equivalent, the best could be either
        possibleBest.add(new Point(-2, -2));
        possibleBest.add(new Point(-1, -1));
        assertTrue(possibleBest.contains(bot.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition()))));

        // Making a second, better, cluster of tiles
        irrigate(-1, 0, UnitVector.J);
        irrigate(-1, 0, UnitVector.K);
        irrigate(-1, 0, UnitVector.L);
        irrigate(-1, 0, UnitVector.M);
        assertEquals(new Point(-1, 0), bot.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition())));

        // Making the second cluster worst than the first one by saturating it with bamboos
        Tile currentGrow = g.getBoard().get(new Point(0, 1));
        while (currentGrow.getBambooSize() < 4) {
            currentGrow.increaseBambooSize(1);
        }
        currentGrow  = g.getBoard().get(new Point(-1, 1));
        while (currentGrow.getBambooSize() < 4) {
            currentGrow.increaseBambooSize(1);
        }

        currentGrow = g.getBoard().get(new Point(-2, 0));
        while (currentGrow.getBambooSize() < 4) {
            currentGrow.increaseBambooSize(1);
        }

        assertTrue(possibleBest.contains(bot.whereToMoveGardener(g, g.getBoard().getAccessiblePositions(g.getGardener().getPosition()))));
    }

    @Test
    public void whereToMovePanda() {
    }

    @Test
    public void planActions() {
    }

    @Test
    public void chooseObjectiveToValidate() {
    }
}