package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.graphs.Edge;
import com.raccoon.takenoko.tool.graphs.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Resource;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class PathFinderTest {

    @Resource(name="preSetBoard")
    Board board;

    @Autowired
    Game game;

    private PathFinderBot pfb;

    @BeforeEach
    void setup() {
        pfb = new PathFinderBot();
    }

    @Test
    void shortestPathTest() {
        assertEquals(1, pfb.shortestPath(board, new Point(0,0), new Point (2,2)).size());

        List<Point> path = pfb.shortestPath(board, new Point(0,0), new Point (-1,2));

        assertEquals(2, path.size());
    }

    @Test
    void pathsTest() {

        Tile start = board.get(new Point(0,0));

        Map<Tile, Map<Tile, List<Tile>>> pathsMatrix = pfb.paths(board, new ArrayList<>(Arrays.asList(start)));

        assertEquals(1, pathsMatrix.get(start).get(board.get(new Point(-2,0))).size());
        assertEquals(2, pathsMatrix.get(start).get(board.get(new Point(1,2))).size());

        assertEquals(0, pathsMatrix.get(start).get(board.get(new Point(0,0))).size());

    }

    @Test
    @Disabled("Doesn't work with the graph construction that remove the extra edges")
    void buildBambooGraphTest() {
        List<Tile> bambooTiles = game.getBoard().getAllTiles().stream().filter(t -> t.getBambooSize() > 0).collect(Collectors.toList());
        if (!bambooTiles.contains(game.getBoard().get(game.getPanda().getPosition()))) {
            bambooTiles.add(game.getBoard().get(game.getPanda().getPosition()));
        }
        Graph boardGraph = pfb.buildBambooGraph(game);
        for (Tile t1 : bambooTiles) {
            for (Tile t2 : bambooTiles) {
                if (!t1.equals(t2)) {
                    assertTrue(boardGraph.getEdges().contains(new Edge(t1, t2, 0)));
                }
            }
        }
    }
}
