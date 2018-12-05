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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;
import java.awt.*;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class PathFinderTest {

    @Resource(name="preSetBoard")
    Board board;

    PathFinderBot pfb = new PathFinderBot();


    @Test
    public void shortestPathTest() {
        assertEquals(2, pfb.shortestPath(board, new Point(0,0), new Point (2,2)));

        assertEquals(2, pfb.shortestPath(board, new Point(0,0), new Point (-2,3)));
    }
}
