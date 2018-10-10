package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.objective.panda.TwoBambooChunksPandaObjective;
import com.raccoon.takenoko.game.objective.parcel.AlignmentParcelObjective;
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
    private List<TwoBambooChunksPandaObjective> bambooObjectives;
    private List<AlignmentParcelObjective> patternObjectives;

    private Deque<Objective> deck;      // The deck of objectives, containing the objectives before they are drawn

    /**
     * Constructs a pool of objectives ready to be drawn in a random order.
     * @param game The Game this pool belongs to
     */
    public ObjectivePool(Game game) {

        // Hookup of the game we belong to
        this.game = game;

        // Instanciation of the lists
        allObjectives = new ArrayList<>();
        bambooObjectives = new ArrayList<>();
        patternObjectives = new ArrayList<>();

        /* Instanciation of the objectives and filling of the lists.
        First version, we juste create 10 card for each colour and each objective.
         */
        for (int i = 0; i < 10; i++) {
            for (Color color : Color.values()) {
                AlignmentParcelObjective newObjective = new AlignmentParcelObjective(color);
                this.allObjectives.add(newObjective);
                patternObjectives.add(newObjective);
            }
        }
        for (int i = 0; i < 10; i++) {
            for (Color color : Color.values()) {
                TwoBambooChunksPandaObjective newObjective = new TwoBambooChunksPandaObjective(color);
                this.allObjectives.add(newObjective);
                bambooObjectives.add(newObjective);
            }
        }

        // Initialisation of the deck
        deck = new ArrayDeque<>();
        Collections.shuffle(allObjectives);
        deck.addAll(allObjectives);
    }

    /**
     * Draws an objective from the deck.
     * @return an objective yet unplayed in this game
     */
    public Objective draw() {
        // TODO : Treat the case of an empty stack, when no more objectives are available
        return this.deck.pop();
    }

    /**
     * Notifies the pool that a tile has been put on the board. The completion checking of the objectives
     * depending on the patterns is triggered.
     * @param tile the tile that has been put down, allows to check only the area where this event happened
     */
    public void notifyTilePut(Tile tile) {
        // We just go through the pattern objectives and check for their completion
        for(Objective objective : patternObjectives) {
            objective.checkIfCompleted(tile, game.getBoard());
        }
    }

    /**
     * Notifies the pool that a player's stomach state has changed. Either he had the panda to eat a bamboo
     * or he validated a bamboo objective, and his stomach has been emptied of some bamboo chunks.
     * @param player the {@code Player} who triggered the action. The changes will have happened in his stomach.
     */
    public void notifyStomachChange(Player player) {
        /*
        Maybe not the best way to do this, but it works
         */

        List<Objective> objectives = player.getObjectives();    // First we get the list of the player's objectives

        for (Objective objective : objectives) {            // For each of these objectives,
            if(this.bambooObjectives.contains(objective)) { // we check if it is a bamboo objective
                // (thanks to its membership in the bamboo objective list)
                objective.checkIfCompleted(player); // If it is, we check for its completion
            }
        }

    }


}
