package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.player.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PandaObjectiveV2Test {

    @Mock
    private Player player;

    @Test
    public void testCheckIfCompleted() {
        Objective obj1 = new PandaObjectiveV2(0, 2, 0, 1);  // Two bamboos of the same color
        Map<Color, Integer> stomach = new EnumMap<>(Color.class);

        when(player.getStomach()).thenReturn(stomach);

        stomach.put(Color.GREEN, 2);
        stomach.put(Color.YELLOW, 1);
        stomach.put(Color.PINK, 0);

        obj1.checkIfCompleted(player);
        assertFalse(obj1.isCompleted());    // We don't have enough, it shouldn't be validated

        stomach.put(Color.YELLOW, 2);
        obj1.checkIfCompleted(player);
        assertTrue(obj1.isCompleted()); // we have enough, it should be completed

        Objective obj2 = new PandaObjectiveV2(1, 1, 1, 1);
        obj2.checkIfCompleted(player);
        assertFalse(obj2.isCompleted());

        stomach.put(Color.PINK, 1);
        obj2.checkIfCompleted(player);
        assertTrue(obj2.isCompleted());
    }
}