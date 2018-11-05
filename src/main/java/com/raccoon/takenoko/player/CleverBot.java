package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.Gardener;
import com.raccoon.takenoko.game.Panda;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.game.objective.ObjectivePool;
import com.raccoon.takenoko.game.objective.ObjectiveType;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.ForbiddenActionException;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CleverBot extends Player {

    private List<Point> pandaNextPosition;
    private List<Point> gardenerNextPosition;
    private List<Point> whereToIrrigate;

    public CleverBot() {
        super();
        pandaNextPosition = new ArrayList<>();
        gardenerNextPosition = new ArrayList<>();
        whereToIrrigate = new ArrayList<>();
    }

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

        return actions.toArray(new Action[0]);
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

    @Override
    public void tileImprovement(Game game, List<Tile> improvableTiles) throws ForbiddenActionException {

    }

    private List<Action> tryToValidatePandaObjective(Game game) {

        List<Action> actions = new ArrayList<>();

        for (Objective objective : this.getObjectives().stream().filter(objective ->
                game.getObjectivePool().getObjectiveType(objective).equals(ObjectiveType.PANDA)).collect(Collectors.toList())) {
            // For each panda objective we own
            /* There, I really think we should have a field ObjectiveType in the Objectives.
            Maybe not the most beautiful thing to do, but it would be much easier to filter the list
            of objectives thanks to that field rather than asking the pool to look in his lists to know whether each
            objective is in them are not.
             */


            List bamboosToEat = bamboosToEatToComplete(objective);  // We get the bamboos we need to eat

            if (bamboosToEat.size() == 1) { // If we just have one bamboo to eat
                // Can I eat a bamboo on a tile of this colour ?

                Board board = game.getBoard();
                Panda panda = game.getPanda();
                Gardener gardener = game.getGardener();

                // We get the list of tiles of the right colour accessible from the panda
                List<Point> rightColourPosition = board.getAccessiblePositions(panda.getPosition()).stream().filter(position ->
                    board.get(position).getColor().equals(bamboosToEat.get(0))
                ).collect(Collectors.toList());

                // Then the list of positions with some bamboos on it
                List<Point> positionsWithBamboo = rightColourPosition.stream().filter(position ->
                        board.get(position).getBambooSize() > 1).collect(Collectors.toList());

                if (!positionsWithBamboo.isEmpty()) {
                    // If we have an accessible position with a bamboo on it of the right colour
                    pandaNextPosition = positionsWithBamboo;    // We remember where we have to move the panda
                    actions.add(Action.MOVE_PANDA);
                    return actions;    // And we say we can do something
                }




                // If I can't, can I grow a bamboo of this colour then eat it ?
                List<Point> accessibleByTheGardenerAsWell = new ArrayList<>(rightColourPosition);
                accessibleByTheGardenerAsWell.retainAll(board.getAccessiblePositions(gardener.getPosition()));

                // Find the irrigated ones :
                List<Point> accessibleByBothPawnsAndIrrigated = accessibleByTheGardenerAsWell.stream().filter(position -> board.get(position).isIrrigated()).collect(Collectors.toList());

                if (!accessibleByBothPawnsAndIrrigated.isEmpty()) {
                    // If there a is a tile of the right colour, irrigated, that is accessible by both the panda and the gardener
                    actions.add(0, Action.MOVE_GARDENER);
                    actions.add(1, Action.MOVE_PANDA);
                    pandaNextPosition = accessibleByBothPawnsAndIrrigated;
                    gardenerNextPosition = accessibleByBothPawnsAndIrrigated;
                }
                else {  // No tile is accessible by both and irrigated but,
                    List<Point> accessibleByBothAndIrrigable = accessibleByTheGardenerAsWell.stream().filter(position -> board.get(position).isIrrigable()).collect(Collectors.toList());

                    if (!accessibleByBothAndIrrigable.isEmpty() && this.getIrrigations() >= 1) {
                        pandaNextPosition = accessibleByBothAndIrrigable;
                        gardenerNextPosition = accessibleByBothAndIrrigable;
                        whereToIrrigate.add(accessibleByBothAndIrrigable.get(0));
                        // Here we just consider the first tile, the rest of the functions will just take the first of the list as well
                        // This is a draft, could be done in a more clever way but will work for this case

                        actions.add(0, Action.PUT_DOWN_IRRIGATION);
                        actions.add(1, Action.MOVE_GARDENER);
                        actions.add(2, Action.MOVE_PANDA);
                    }

                }


            }



        }
        return actions;
    }

    private List<Color> bamboosToEatToComplete(Objective objective) {
        /* COULDN'T THIS BE PART OF THE OBJECTIVE INSTEAD ? */
        /* Computes the difference between our stomach and the goal needed to complete the objective
        * Returns a list of colors : one for each bamboo we need to eat */

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

        List<Color> bamboosToEat = new ArrayList<>();

        // For each color, we add as many copy as their value in the list of bamboos to eat
        stomachsDiff.forEach((key, value) ->
            bamboosToEat.addAll(Collections.nCopies(value, key))
        );

        return bamboosToEat;
    }
}
