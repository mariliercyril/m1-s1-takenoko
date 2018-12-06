package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Resource;
import java.awt.Point;
import java.util.List;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class PathFinderTest {

    @Resource(name="preSetBoard")
    Board board;

    private PathFinderBot pfb = new PathFinderBot();


    @Test
    void shortestPathTest() {
        assertEquals(0, pfb.shortestPath(board, new Point(0,0), new Point (2,2)).size());

        List<Point> path = pfb.shortestPath(board, new Point(0,0), new Point (-1,2));

        assertEquals(1, path.size());
    }
}
