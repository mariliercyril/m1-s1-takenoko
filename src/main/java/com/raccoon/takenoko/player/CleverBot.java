package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CleverBot extends Player {

    @Override
    protected Action[] planActions(Game game) {

        List<Action> actions = new ArrayList<>();

        // First, we check if we can validate some objective
        for (Objective objective : this.getObjectives()) {
            if (objective.isCompleted()) {
                actions.add(Action.VALID_OBJECTIVE);
            }
        }


        if(this.getObjectives().size() < Constants.MAX_AMOUNT_OF_OBJECTIVES) { // If we can get a new objective
            actions.add(Action.DRAW_OBJECTIVE);
            // Choose second action
        }
        else { // We can choose two actions
            // 1) Check if we can validate a panda objective in this or the next turn -> do it
            // 2) Check if we can validate a gardener objective in this turn or the next one -> do it
            // 3) Check if we can improve the completion of a panda objective -> store it
            // 4) Check if we can improve the completion of a gardener objective -> store it
            // 5) Compare 4 and 5 to see which one will be the quicker to validate, with a priority to panda
            // 6) Or compare 4 and 5 to see which objective improve the most the completion of others ?
        }
        return new Action[0];
    }

    @Override
    protected ObjectiveType whatTypeToDraw(ObjectivePool pool) {

        ObjectiveType typeToDraw = null;

        if (pool.isDeckEmpty(ObjectiveType.PANDA)) {     // We try to get a panda one
            typeToDraw = ObjectiveType.PANDA;
        }
        else {
            if (!pool.isDeckEmpty(ObjectiveType.GARDENER)) {  // if we can't we try a gardener
                typeToDraw = ObjectiveType.GARDENER;
            }
            else {
                if (!pool.isDeckEmpty(ObjectiveType.PATTERN)) {
                    typeToDraw = ObjectiveType.PATTERN;
                }
            }
        }
        return typeToDraw;
    }

    @Override
    public boolean keepIrrigation() {
        return false;
    }

    @Override
    protected boolean putDownIrrigation(Game game) {
        return false;
    }


    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
        return null;
    }

    @Override
    protected Tile chooseTile(Game game) {
        return null;
    }

    @Override
    protected Point whereToMoveGardener(Game game, List<Point> available) {
        return null;
    }

    @Override
    protected Point whereToMovePanda(Game game, List<Point> available) {
        return null;
    }

    @Override
    protected Objective chooseObjectiveToValidate() {
        return null;
    }

    private boolean canWeValidatePandaObjective(Game game) {

        for (Objective objective : this.getObjectives()) {
            /* There, I really think we should have a field ObjectiveType in the Objectives.
            Maybe not the most beautiful thing to do, but it would be much easier to filter the list
            of objectives thanks to that field rather than asking the pool to look in his lists to know whether each
            objective is in them are not.
             */
            if (game.getObjectivePool().getObjectiveType(objective).equals(ObjectiveType.PANDA)) {
                // For each panda objective we own

                Map diff = stomachDiff(objective);




            }
        }
        return false;
    }

    private Map<Color, Integer> stomachDiff(Objective objective) {
        /* Computes the difference between our stomach and the goal needed to complete the objective */

        Map<Color, Integer> goalStomach = objective.getPatternForCompleting();

        // We initialize a "diff" stomach, that will contain the difference between our stomach and the goal
        Map<Color, Integer> stomachsDiff = new EnumMap<>(Color.class);
        for (Color color : Color.values()) {
            stomachsDiff.put(color, 0);
        }

        goalStomach.forEach((key, value) -> {
            int difference;
            if ((difference = value - this.getStomach().get(key)) > 0) {
                // If we need to eat more bamboo
                stomachsDiff.put(key, difference); // We remember how many
            }
        });

        return stomachsDiff;
    }
}
