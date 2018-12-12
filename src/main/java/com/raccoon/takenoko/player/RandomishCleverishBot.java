package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.tool.Constants;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RandomishCleverishBot extends RandomBot {

    @Override
    protected Action[] planActions(Game game) {
        if (this.getObjectives().size() < Constants.MAX_AMOUNT_OF_OBJECTIVES) {
            return new Action[]{Action.MOVE_PANDA, Action.DRAW_OBJECTIVE, Action.VALID_OBJECTIVE};
        }
        return new Action[]{Action.MOVE_PANDA, Action.VALID_OBJECTIVE};
    }

    @Override
    protected Point whereToMovePanda(Game game, List<Point> available) {
        Point safetyNet = available.get(0);
        available.removeIf(p -> game.getBoard().get(p).getBambooSize() == 0);
        if (available.isEmpty()) {
            return safetyNet;
        } else {
            Collections.shuffle(available);
            return available.get(0);
        }
    }

    @Override
    protected ObjectiveType whatTypeToDraw(ObjectivePool pool) {
        return ObjectiveType.PANDA;
    }
}