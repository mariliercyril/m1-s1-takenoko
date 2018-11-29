package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class PandaOnlyObjPoolTest {

    @Mock
    Player player;

    @Mock
    Board board;

    private ObjectivePool objectivePool;

    private Objective gardenerObjective;
    private Objective pandaObjective;
    private Objective patternObjective;

    private Map<Color,Integer> stomach;

    @BeforeEach
    void setUp(@Autowired @Qualifier("pandaOnlyObjPool") ObjectivePool objectivePool) {

        this.objectivePool = objectivePool;

        this.gardenerObjective = this.objectivePool.draw(ObjectiveType.GARDENER);
        this.pandaObjective = this.objectivePool.draw(ObjectiveType.PANDA);
        this.patternObjective = this.objectivePool.draw(ObjectiveType.PATTERN);

        this.stomach = new EnumMap<>(Color.class);


    }

    @Test
    void draw() {

        assertNotNull(pandaObjective, "First panda objective drawn is null");

        assertNull(gardenerObjective, "First gardener objective drawn is null");
        assertNull(patternObjective, "First pattern objective drawn is null");

        assertTrue(pandaObjective instanceof PandaObjective, "Drawn a panda objective, ended up with another kind of objective…");
    }

    @Test
    void notifyBambooEaten() {

        when(player.getStomach()).thenReturn(stomach);
        when(player.getObjectives()).thenReturn(new ArrayList<>(Arrays.asList(this.pandaObjective)));

        stomach.put(Color.YELLOW, 2);
        stomach.put(Color.PINK, 2);
        stomach.put(Color.GREEN, 2);

        objectivePool.notifyBambooEaten(board, player);

        assertTrue(this.pandaObjective.isCompleted(), "2 bamboos of each kind in the stomach and still not a panda objective completed ?");

        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
        stomach.put(Color.GREEN, 0);

        objectivePool.notifyStomachEmptied(player);

        assertFalse(this.pandaObjective.isCompleted(), "No more bamboos in the stomach, still a panda objective completed");

    }

    @Test
    void emptyDeckTest() {
        assertTrue(objectivePool.isDeckEmpty(ObjectiveType.GARDENER), "The deck of gardener objective is not said to be empty");
        assertTrue(objectivePool.isDeckEmpty(ObjectiveType.PATTERN), "The deck of pattern objective is not said to be empty");
        assertFalse(objectivePool.isDeckEmpty(ObjectiveType.PANDA), "The deck of panda objective is said to be empty");
    }
}