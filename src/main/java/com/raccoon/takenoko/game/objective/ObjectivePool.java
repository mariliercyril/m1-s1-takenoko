package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.*;
import com.raccoon.takenoko.game.objective.parcel.AlignmentParcelObjective;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;

import java.util.*;

/**
 * The pool of objectives needed in a game, and all the means to interact with it.
 * Provides the notify methods to induce a completion checking for the right objectives.
 */
public class ObjectivePool {

    private Game game;      // The game to which this pool belongs

    private List<Objective> allObjectives;  // The list of all the objectives in the game, maybe useless

    /*
    The list of objectives by type, needed to check for the completion of a specific
    type of objective. Useful because not all the events affect all the objectives completion.
    ***** CAUTION ***** : This is a draft, has to be updated with an interface or a mother class
    for all the types. For now we just have one objective per type.
    */
    private List<Objective> bambooObjectives;
    private List<Objective> patternObjectives;
    private List<Objective> gardenerObjectives;

    private EnumMap<ObjectiveType, List<Objective>> deck;

    /**
     * Constructs a pool of objectives ready to be drawn in a random order.
     *
     * @param game The Game this pool belongs to
     */
    public ObjectivePool(Game game) {

        // Hookup of the game we belong to
        this.game = game;

        // Instanciation of the lists
        bambooObjectives = new ArrayList<>();
        patternObjectives = new ArrayList<>();
        gardenerObjectives = new ArrayList<>();

        // instanciation of the generic deck
        deck = new EnumMap<>(ObjectiveType.class);
        deck.put(ObjectiveType.PATTERN, patternObjectives);
        deck.put(ObjectiveType.PANDA, bambooObjectives);
        deck.put(ObjectiveType.GARDENER, gardenerObjectives);

        /* Instanciation of the objectives and filling of the lists.
        First version, we juste create 10 card for each colour and each objective.
         */
        for (int i = 0; i < 10; i++) {
            for (Color color : Color.values()) {
                AlignmentParcelObjective newObjective = new AlignmentParcelObjective(color);
                patternObjectives.add(newObjective);
            }
        }
        Collections.shuffle(patternObjectives);
        for (int i = 0; i < 10; i++) {
            for (Color color : Color.values()) {
                PandaObjective newObjective = new PandaObjective(color);
                bambooObjectives.add(newObjective);
            }
        }
        Collections.shuffle(bambooObjectives);
        for (int i = 1; i < 4; i++) {
            for (Color color : Color.values()) {
                GardenerObjective newGObjective = new GardenerObjective(i, color, 1);
                gardenerObjectives.add(newGObjective);
            }
        }
        Collections.shuffle(gardenerObjectives);
    }

    /**
     * Draws an objective from the deck.
     *
     * @return an objective yet unplayed in this game ATTENTION can be null if asked deck is empty
     */
    public Objective draw(ObjectiveType t) {
        if (!deck.get(t).isEmpty()) { return deck.get(t).get(0); }
        return null;
    }

    /**
     * Notifies the pool that a tile has been put on the board. The completion checking of the objectives
     * depending on the patterns is triggered.
     *
     * @param tile the tile that has been put down, allows to check only the area where this event happened
     */
    public void notifyTilePut(Tile tile) {
        updatePatternObjectives(tile);
    }

    /**
     * Notifies the pool that a player's stomach state has changed. Either he had the panda to eat a bamboo
     * or he validated a bamboo objective, and his stomach has been emptied of some bamboo chunks.
     *
     * @param player the {@code Player} who triggered the action. The changes will have happened in his stomach.
     */
    public void notifyStomachEmpty(Player player) {
        updatePandaObjectives(player);
    }

    public void notifyBambooEaten(Board b, Player p) {
        updatePandaObjectives(p);
        updateGardenerObjectives(b);
    }


    public void notifyBambooGrowth(Board b) {
        updateGardenerObjectives(b);
    }

    private void updatePatternObjectives(Tile t) {
        for (Objective objective : patternObjectives) {
            objective.checkIfCompleted(t, game.getBoard());
        }
    }

    private void updatePandaObjectives(Player p) {

        List<Objective> objectives = p.getObjectives();    // First we get the list of the player's objectives

        for (Objective objective : objectives) {            // For each of these objectives,
            if (this.bambooObjectives.contains(objective)) { // we check if it is a bamboo objective
                // (thanks to its membership in the bamboo objective list)
                objective.checkIfCompleted(p); // If it is, we check for its completion
            }
        }
    }

    private void updateGardenerObjectives(Board b) {
        for (Objective go : gardenerObjectives) {
            go.checkIfCompleted(b);
        }
    }

    public boolean isDeckEmpty(ObjectiveType t) {
        return deck.get(t).isEmpty();
    }

}
