package com.raccoon.takenoko.game.objective.panda;

import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.player.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito.*;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwoBambooChunksPandaObjectiveTest {

    private TwoBambooChunksPandaObjective test;

    @Before
    public void preTest() {
        test = new TwoBambooChunksPandaObjective(Color.GREEN);
    }

    @Test
    public void checkIfCompleted() {
        HashMap<Color, Integer> mockStomach = new HashMap<>();  // A mocked stomach
        mockStomach.put(Color.GREEN, 0);
        mockStomach.put(Color.YELLOW, 0);
        mockStomach.put(Color.PINK, 0);
        // Creating a mock player that returns the mock stomach
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getStomach()).thenReturn(mockStomach);
        // First we check if the objective isn't completed when it actually isn't
        test.checkIfCompleted(mockPlayer);
        assertFalse(test.isCompleted());

        mockStomach.put(Color.PINK, 2); // Then we get two pink bamboos in there to see if the green objective gets validated by the pink bamboos

        test.checkIfCompleted(mockPlayer);
        assertFalse(test.isCompleted()); // Spoiler : it should not

        mockStomach.put(Color.GREEN, 2); // If we try the same thing with green bamboos it should work

        test.checkIfCompleted(mockPlayer);
        assertTrue(test.isCompleted()); // Fingers crossed
    }
}