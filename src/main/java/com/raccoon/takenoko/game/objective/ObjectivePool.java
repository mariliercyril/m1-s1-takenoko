package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.objective.panda.TwoBambooChunksPandaObjective;
import com.raccoon.takenoko.game.objective.parcel.AlignmentParcelObjective;

import java.util.*;

/**
 * The pool of objectives needed in a game, and all the means to interact with it.
 * Provides the notify methods to induce a completion checking for the right objectives.
 */
public class ObjectivePool {

    private List<Objective> allObjectives;      // The list of all the objectives in the game

    /*
    The list of objectives by type, needed to check for the completion of a specific
    type of objective. Useful because not all the events affect all the objectives completion.
    ***** CAUTION ***** : This is a draft, has to be updated with an interface or a mother class
    for all the types. For now we just have one objective at per type.
    */
    private List<TwoBambooChunksPandaObjective> bambooObjectives;
    private List<AlignmentParcelObjective> patternObjectives;

    private Stack<Objective> deck;      // The deck of objectives, containing the objectives before they are drawn

    /**
     * Constructs a pool of objectives ready to be drawn in a random order.
     */
    public ObjectivePool() {

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
        deck = new Stack<>();
        deck.addAll(allObjectives);
        Collections.shuffle(deck);
    }

    /**
     * Draws an objective from the deck.
     * @return an objective yet unplayed in this game
     */
    public Objective draw() {
        // TODO : Treat the case of an empty stack, when no more objectives are available
        return this.deck.pop();
    }


}
