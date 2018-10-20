package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.UnitVector;

import java.awt.Point;
import java.util.*;

public class RandomBot extends Player {

    public RandomBot() {
        super();
    }

    /**
     * Puts down a tile at a random available location on the board given by the game
     *
     * @param game the game in which the player is playing
     */
    @Override
    protected Point whereToPutDownTile(Game game, Tile t) {
        Board b = game.getBoard();
        List availablePositions = b.getAvailablePositions();
        Collections.shuffle(availablePositions);

        Point playingPos;
        playingPos = (Point) availablePositions.get(0);
        return playingPos;
    }

    @Override
    protected Tile chooseTile(Game game) {  // Randomly chooses one tile out of three
        Random rand = new Random();
        List<Tile> tiles = game.getTiles();
        int choice = rand.nextInt() % tiles.size();
        if (choice < 0) {
            choice *= -1;
        }
        for (int i = 0; i < tiles.size(); i++) {       // The players put the tiles he doesnt want back in the deck
            if (i != choice) {
                game.putBackTile(tiles.get(i));
            }
        }
        return tiles.get(choice);
    }

    @Override
    protected Point whereToMoveGardener(Game game, List<Point> available) {
        Collections.shuffle(available);
        return available.get(0);

    }

    @Override
    protected Point whereToMovePanda(Game game, List<Point> available) {
        Collections.shuffle(available);
        return available.get(0);
    }

    @Override
    protected Action[] planActions(Game game) {
        int score = 0, cursor = 0;
        Random r = new Random();
        ArrayList<Action> choosen = new ArrayList<>();
        Action[] available = Action.values();

        // if there is no tile yet, we NEED to put one down
        if (game.getBoard().getNeighbours(new Point(0, 0)).size() <= 0) {
            choosen.add(Action.PUT_DOWN_TILE);
            score += Action.PUT_DOWN_TILE.getCost();
            cursor++;
        }

        while (score < 2) {
            if (r.nextBoolean() && r.nextBoolean()) {
                // ban unavailable actions
                // can't put irrigation if none has been taken
                if (available[cursor] == Action.PUT_DOWN_IRRIGATION && this.getIrrigations() <= 0) {
                    cursor = ++cursor % available.length;
                    continue;
                }
                // can't validate an objective if I don't have any
                if (available[cursor] == Action.VALID_OBJECTIVE && this.chooseObjectiveToValidate() == null) {
                    cursor = ++cursor % available.length;
                    continue;
                }
                // can't draw objective if I already have 2
                if (available[cursor] == Action.DRAW_OBJECTIVE && this.getObjectives().size() >= Constants.MAX_AMOUNT_OF_OBJECTIVES) {
                    cursor = ++cursor % available.length;
                    continue;
                }
                // can't play the same action twice
                if (choosen.contains(available[cursor])) {
                    cursor = ++cursor % available.length;
                    continue;
                }

                choosen.add(available[cursor]);
                score += available[cursor].getCost();
            }
            cursor = ++cursor % available.length;
        }

        return choosen.toArray(new Action[0]);
    }

    @Override
    protected Objective chooseObjectiveToValidate() {

        for (Objective objective : this.getObjectives()) {  // We go through all the objectives

            if (objective.isCompleted()) {   // If we find one completed,
                return objective;           // we return it
            }

        }
        Collections.shuffle(this.getObjectives());
        if (this.getObjectives().size() > 0) { return this.getObjectives().get(0); }

        return null;    // If no objective is completed, we just return null
    }

    @Override
    public boolean keepIrrigation() {
        return new Random().nextBoolean();
    }

    @Override
    protected boolean putDownIrrigation(Game game) {
        List<Tile> boardTiles = game.getBoard().getAllTiles();
        Collections.shuffle(boardTiles);
        boardTiles.removeIf(p -> p.getPosition().equals(new Point(0, 0)));

        UnitVector[] directionsTable = UnitVector.values();
        Collections.shuffle(Arrays.asList(directionsTable));

        return putDownIrrigation(game, boardTiles.get(0).getPosition(), directionsTable[0]);
    }
}
