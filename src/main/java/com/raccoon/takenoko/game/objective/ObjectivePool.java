package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public abstract class ObjectivePool {

    protected Game game;      // The game to which this pool belongs

    /*
    The list of objectives by type, needed to check for the completion of a specific
    type of objective. Useful because not all the events affect all the objectives completion.
    ***** CAUTION ***** : This is a draft, has to be updated with an interface or a mother class
    for all the types. For now we just have one objective per type.
    */
    List<Objective> bambooObjectives;
    List<Objective> patternObjectives;
    List<Objective> gardenerObjectives;

    EnumMap<ObjectiveType, List<Objective>> deck;

    protected ObjectivePool() {
        // Instanciation of the lists
        this.bambooObjectives = new ArrayList<>();
        this.patternObjectives = new ArrayList<>();
        this.gardenerObjectives = new ArrayList<>();

        // instanciation of the generic deck
        this.deck = new EnumMap<>(ObjectiveType.class);
        this.deck.put(ObjectiveType.PATTERN, new ArrayList<>());
        this.deck.put(ObjectiveType.PANDA, new ArrayList<>());
        this.deck.put(ObjectiveType.GARDENER, new ArrayList<>());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Draws an objective from the deck.
     *
     * @return an objective yet unplayed in this game ATTENTION can be null if asked deck is empty
     */
    public Objective draw(ObjectiveType t) {
        if (!deck.get(t).isEmpty()) { return deck.get(t).remove(0); }
        return null;
    }

    /**
     * Notifies the pool that a tile has been put on the board. The completion checking of the objectives
     * depending on the patterns is triggered.
     *
     * @param tile the tile that has been put down, allows to check only the area where this event happened
     */
    public void notifyTilePut(Tile tile, Board b) {
        updatePatternObjectives(tile, b);
    }

    /**
     * Notifies the pool that a player's stomach state has changed. Either he had the panda to eat a bamboo
     * or he validated a bamboo objective, and his stomach has been emptied of some bamboo chunks.
     *
     * @param player the {@code Player} who triggered the action. The changes will have happened in his stomach.
     */
    public void notifyStomachEmptied(Player player) {
        updatePandaObjectives(player);
    }

    public void notifyBambooEaten(Board b, Player p) {
        updatePandaObjectives(p);
        updateGardenerObjectives(b);
    }

    public void notifyBambooGrowth(Board b) {
        updateGardenerObjectives(b);
    }

    private void updatePatternObjectives(Tile t, Board b) {
        for (Objective objective : patternObjectives) {
            for(Tile next : b.getAllTilesDistance(t.getPosition(), 2)){
                objective.checkIfCompleted(next, game.getBoard());
            }
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

    /**
     * Allows to get the type of an objective, without having to use the {@code instanceof} tool.
     * @param objective An Objective
     * @return the type of the objective
     */
    public ObjectiveType getObjectiveType(Objective objective) {

        if (this.bambooObjectives.contains(objective)) return ObjectiveType.PANDA;
        if (this.gardenerObjectives.contains(objective)) return ObjectiveType.GARDENER;
        if (this.patternObjectives.contains(objective)) return ObjectiveType.PATTERN;

        throw new RuntimeException("Objective not member of this pool");

    }



}
