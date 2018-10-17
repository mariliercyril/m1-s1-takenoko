package com.raccoon.takenoko.player;

import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.Objective;
import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.objective.panda.PandaObjective;
import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.ForbiddenActionException;
import com.raccoon.takenoko.tool.Vector;

import java.awt.Point;
import java.util.*;

/**
 * Class representig the player taking part in the game. To be extended by a bot to
 * actually perform a move when it's its turn to play.
 * Will provide all the attributes and methods common to all players.
 */
public abstract class Player {

    private int score;
    private int id;
    private List<Objective> objectives;
    private static int counter = 0;
    private HashMap<Color, Integer> stomach;
    private int irrigations;

    public Player() {
        score = 0;
        counter++;
        id = counter;
        objectives = new ArrayList<>();
        stomach = new HashMap<>();
        stomach.put(Color.GREEN, 0);
        stomach.put(Color.YELLOW, 0);
        stomach.put(Color.PINK, 0);
        irrigations = 0;
    }

    public int getScore() {
        return score;
    }

    public int getId() {
        return id;
    }

    public int getIrrigations() {
        return irrigations;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void addObjective(Objective objective) {
        this.objectives.add(objective);
    }

    public HashMap<Color, Integer> getStomach() {
        return stomach;
    }

    /**
     * This method will be the one called by the game to give their turn to the players/
     * It will be calling the methods for the players to play their turn.
     * <p>
     * DESIGN PATTERN : TEMPLATE METHOD
     *
     * @param game the game in which the player is playing
     */
    public final void play(Game game) throws ForbiddenActionException {
        // 1st step : ask bot to plan actions
        Action[] plannedActions = planActions(game);

        // check if the actions are compatible (exactly 2 costly actions)
        int validityCheck = 0;
        for (int i = 0; i < plannedActions.length; validityCheck += plannedActions[i++].getCost()) { ; }
        if (validityCheck != 2) {
            throw new ForbiddenActionException("Player tried to play an incorrect number of actions.");
        }
        Takeyesntko.print("Choosen actions : " + Arrays.toString(plannedActions));

        // step 2 : execute all actions
        for (Action a : plannedActions) {
            execute(a, game);
        }

        // step 3 : count points
        Takeyesntko.print("Player has played. Current score : " + getScore());
    }

    /**
     * BOT CAN'T ACCESS THIS METHOD
     * Used to enforce a honest behavior
     *
     * @param a    action to play
     * @param game current game
     */
    private void execute(Action a, Game game) throws ForbiddenActionException {
        Takeyesntko.print("PLAYING " + a);


        switch (a) {
            case PUT_DOWN_TILE:
                // refactorable : chooseTile can return a tile with the chosen position in it.
                Tile t = this.chooseTile(game);
                Point choice = this.whereToPutDownTile(game, t);
                t.setPosition(choice);
                game.putDownTile(t);
                break;
            case MOVE_GARDENER:
                List<Point> gardenerAccessible = game.getBoard().getAccessiblePositions(game.getGardener().getPosition());
                Point whereToMoveGardener = whereToMoveGardener(game, gardenerAccessible);
                // check that point is in available points array
                if (!gardenerAccessible.contains(whereToMoveGardener)) {
                    throw new ForbiddenActionException("Player tried to put the gardener in a non accessible position.");
                }
                game.getGardener().move(game.getBoard(), whereToMoveGardener);
                break;
            case DRAW_IRRIGATION:
                Takeyesntko.print("Player drew an irrigation");
                this.irrigations++;
                if (this.keepIrrigation()) {
                    Takeyesntko.print(String.format("Player chooses to keep it. He now has %d irrigations.", irrigations));
                    break;
                }
                if (!this.putDownIrrigation(game)) {
                    Takeyesntko.print(String.format("He can't put it down where he chooses to. He keeps it, he now has %d irrigations.", irrigations));
                    break;
                }
                Takeyesntko.print(String.format("Player has put down an irirgation ! He now has %d irrigations.", irrigations));
                break;
            case VALID_OBJECTIVE:
                Objective objective = this.chooseObjectiveToValidate();
                validateObjective(objective);
                // The completion of bamboo objective could have changed : we removed bamboo from our stomach
                game.getObjectivePool().notifyStomachChange(this);
                break;
            case DRAW_OBJECTIVE:
                if (objectives.size() > Constants.MAX_AMOUNT_OF_OBJECTIVES) {    // We check if we are allowed to add an objective
                    throw new ForbiddenActionException("Player tried to draw an objective with a full hand already");
                }
                objectives.add(game.drawObjective());
                break;
            case PUT_DOWN_IRRIGATION:
                if (this.putDownIrrigation(game)) {
                    Takeyesntko.print("Player put down an irrigation that he had in his stock ! He now have " + irrigations);
                }
                break;
            case MOVE_PANDA: // Works the same way as MOVE_GARDENER except it's a panda
                List<Point> pandaAccessible = game.getBoard().getAccessiblePositions(game.getPanda().getPosition());
                Point whereToMovePanda = whereToMovePanda(game, pandaAccessible);
                if (!pandaAccessible.contains(whereToMovePanda)) {
                    throw new ForbiddenActionException("Player tried to put the panda in a non accessible position.");
                }
                boolean destinationHadBamboo = game.getBoard().get(whereToMovePanda).getBambooSize() > 0; // Checks if there is bamboo on the destination tile
                game.getPanda().move(game.getBoard(), whereToMovePanda);

                if (destinationHadBamboo) {
                    eatBamboo(game.getBoard().get(game.getPanda().getPosition()).getColor()); // The panda eats a piece of bamboo on the tile where it lands
                }
                game.getObjectivePool().notifyStomachChange(this);  // Notification that something changed in our stomach
                break;
            default:
                Takeyesntko.print(a + " UNSUPPORTED");
        }
    }

    public abstract boolean keepIrrigation();

    private void validateObjective(Objective objective) {
        if (objective != null) {
            Takeyesntko.print("Player has completed an objective ! " + objective);
            this.objectives.remove(objective);
            this.score += objective.getScore();

            if (objective instanceof PandaObjective) {
                /* Be careful, the number here has to be changed when we'll have objective involving a
                   different amount of bamboos. This action could be managed by the objectives themselves
                   or by the Game maybe.
                 */
                this.stomach.put(objective.getColor(), this.stomach.get(objective.getColor()) - 2);
            }
        }
    }

    protected abstract boolean putDownIrrigation(Game game);

    protected abstract Action[] planActions(Game game);

    protected abstract Point whereToPutDownTile(Game game, Tile t);

    protected abstract Tile chooseTile(Game game);

    public static void reinitCounter() {
        counter = 0;
    }

    protected abstract Point whereToMoveGardener(Game game, List<Point> available);

    protected abstract Point whereToMovePanda(Game game, List<Point> available);

    protected void eatBamboo(Color color) {
        if (Objects.nonNull(color)) {
            stomach.put(color, stomach.get(color) + 1);
            Takeyesntko.print(String.format("Player has eaten a %s bamboo ! He now has %d %s bamboo(s) in his stomach", color, stomach.get(color), color));
        }
    }

    protected abstract Objective chooseObjectiveToValidate();

    public final boolean putDownIrrigation(Game game, Point pos, Vector direction) {
        if (irrigations > 0 && game.getBoard().canIrrigate(pos, direction)) {
            irrigations--;
            return game.getBoard().irrigate(pos, direction);
        }
        return false;
    }
}
