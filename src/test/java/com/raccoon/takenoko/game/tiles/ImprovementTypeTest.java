package com.raccoon.takenoko.game.tiles;

import com.raccoon.takenoko.game.Game;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ImprovementTypeTest {

    @Test
    public void improve() {
        Game game = new Game();
        Tile t1 = new Tile(Color.GREEN);
        Tile t2 = new Tile(Color.YELLOW);
        Tile t3 = new Tile(Color.YELLOW);

        game.getBoard().set(new Point(0,1), t1);
        game.getBoard().set(new Point(-1, 0), t2);
        game.getBoard().set(new Point(-1, 1), t3);

        // Initial conditions : only the tiles around the pond should be irrigated and have a piece of bamboo
        assertEquals(1, t2.getBambooSize());
        assertEquals(0, t3.getBambooSize());

        // Improving the tile in (-1,1) with a watershed
        ImprovementType.WATERSHED.improve(t3);

        // Testing the effects of the watershed
        assertEquals(1, t3.getBambooSize());
        assertTrue(t3.isIrrigated());
        game.getGardener().move(game.getBoard(), new Point(-1, 0));

        assertEquals(2, t3.getBambooSize());

        // Improving the tile in (-1, 0) with an enclosure
        ImprovementType.ENCLOSURE.improve(t2);

        // Testing the effects of the enclosure
        assertEquals(2, t2.getBambooSize());
        game.getPanda().move(game.getBoard(), new Point(-1, 0));
        assertEquals(2, t2.getBambooSize());

        // Improving the tile in (0, 1) with fertilizer
        ImprovementType.FERTILIZER.improve(t1);

        // Testing the effects of the fertilizer
        assertEquals(1, t1.getBambooSize());
        game.getGardener().move(game.getBoard(), new Point(0,1));
        assertEquals(3, t1.getBambooSize());
    }
}