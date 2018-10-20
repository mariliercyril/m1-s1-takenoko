package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.player.Player;

import java.util.EnumMap;
import java.util.Map;

public class PandaObjectiveV2 extends Objective {

    Map<Color, Integer> goal;

    public PandaObjectiveV2(int green, int yellow, int pink, int value) {  // Number of bamboos of each color needed to satisfy the objective and the point-value of the objective
        super();

        goal = new EnumMap<>(Color.class);
        goal.put(Color.GREEN, green);
        goal.put(Color.YELLOW, yellow);
        goal.put(Color.PINK, pink);

        this.score = value;
    }

    @Override
    public void checkIfCompleted(Player player) {
        Map<Color, Integer> stomach = player.getStomach();

        this.isCompleted = true;    // We suppose the objective is completed

        for (Color c : goal.keySet()) { // We check that it actually is
            if (stomach.get(c) < goal.get(c)) {
                this.isCompleted = false;   // If it isn't well... It isn't
            }
        }
    }
}
