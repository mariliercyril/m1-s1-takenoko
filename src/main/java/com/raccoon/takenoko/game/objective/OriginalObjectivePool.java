package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.*;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.tool.UnitVector;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The pool of objectives needed in a game, and all the means to interact with it.
 * Provides the notify methods to induce a completion checking for the right objectives.
 */
@Service
@Scope("prototype")
public class OriginalObjectivePool extends ObjectivePool {

    /**
     * Constructs a pool of objectives ready to be drawn in a random order.
     *
     */
    public OriginalObjectivePool() {

        super();

        // Instanciation of the objectives and filling of the lists.
        // pattern objectives :
        List<PatternObjective> allPatterns = this.getPatternObjectives();
        this.patternObjectives.addAll(allPatterns);
        Collections.shuffle(patternObjectives);
        this.deck.get(ObjectiveType.PATTERN).addAll(allPatterns);

        // Panda objectives
        this.bambooObjectives.addAll(getPandaObjectives());
        this.deck.get(ObjectiveType.PANDA).addAll(this.bambooObjectives);
        Collections.shuffle(bambooObjectives);

        // Gardener objectives
        this.gardenerObjectives.addAll(this.getGardenerObjectives());
        Collections.shuffle(this.gardenerObjectives);
        this.deck.get(ObjectiveType.GARDENER).addAll(this.gardenerObjectives);
    }


    private List<PatternObjective> getPatternObjectives(){
        ArrayList<PatternObjective> patterns = new ArrayList<>();

        // alignment
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.GREEN, 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.YELLOW, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.J)), Color.PINK, 4));

        // V shape
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.GREEN, 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.YELLOW, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.I)), Color.PINK, 4));

        // triangle
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.GREEN, 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.YELLOW, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.J, UnitVector.N)), Color.PINK, 4));

        // Z single color
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), Color.GREEN, 3));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), Color.YELLOW, 4));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), Color.PINK, 5));

        // Z dual color
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.GREEN, Color.GREEN, Color.YELLOW, Color.YELLOW)), 2));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.GREEN, Color.GREEN, Color.PINK, Color.PINK)), 4));
        patterns.add(new PatternObjective(new ArrayList<>(Arrays.asList(UnitVector.I, UnitVector.M, UnitVector.I)), new ArrayList<>(Arrays.asList( Color.PINK, Color.PINK, Color.YELLOW, Color.YELLOW )), 5));

        return patterns;
    }

    private List<GardenerObjective> getGardenerObjectives() { // 5 6 7 vert jaune rose
        ArrayList<GardenerObjective> bambooPatterns = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            bambooPatterns.add(new GardenerObjective(4, Color.GREEN, 5));
            bambooPatterns.add(new GardenerObjective(4, Color.YELLOW, 6));
            bambooPatterns.add(new GardenerObjective(4, Color.PINK, 7));
        }


        return  bambooPatterns;
    }

    private List<PandaObjective> getPandaObjectives() {
        ArrayList<PandaObjective> pandaObjectives = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            pandaObjectives.add(new PandaObjective(2,0,0,3));
            pandaObjectives.add(new PandaObjective(0,2,0,4));
            pandaObjectives.add(new PandaObjective(0,0,2, 5));
            pandaObjectives.add(new PandaObjective(1,1,1, 6));
        }

        return pandaObjectives;
    }

}
