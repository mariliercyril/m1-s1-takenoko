package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.tool.Constants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Scope("prototype")
public class PandaOnlyObjPool extends ObjectivePool {

    private Map<Color, Integer> bamboosNeeded;

    public PandaOnlyObjPool() {
        super();

        this.bamboosNeeded = new EnumMap<Color, Integer>(Color.class);

        for (Color color : Color.values()) {
            bamboosNeeded.put(color, 0);
        }


        List<PandaObjective> pandaObjectives = getPandaObjectives();

        Collections.shuffle(pandaObjectives);

        this.bambooObjectives.addAll(pandaObjectives);
        this.deck.get(ObjectiveType.PANDA).addAll(this.bambooObjectives);



    }

    public Map<Color, Integer> getBamboosNeeded() {
        return bamboosNeeded;
    }

    private List<PandaObjective> getPandaObjectives() {

        ArrayList<PandaObjective> pandaObjectives = new ArrayList<>();

        for (int i = 0; i < Constants.NUMBER_OF_2_GREEN_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(2,0,0,3));
            bamboosNeeded.put(Color.GREEN, bamboosNeeded.get(Color.GREEN) + 2);
        }

        for (int i = 0; i < Constants.NUMBER_OF_2_YELLOW_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(0,2,0,4));
            bamboosNeeded.put(Color.YELLOW, bamboosNeeded.get(Color.YELLOW) + 2);
        }

        for (int i = 0; i < Constants.NUMBER_OF_2_PINK_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(0,0,2, 5));
            bamboosNeeded.put(Color.PINK, bamboosNeeded.get(Color.PINK) + 2);
        }

        for (int i = 0; i < Constants.NUMBER_OF_3_CHUNKS_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(1,1,1, 6));
            bamboosNeeded.put(Color.GREEN, bamboosNeeded.get(Color.GREEN) + 1);
            bamboosNeeded.put(Color.YELLOW, bamboosNeeded.get(Color.YELLOW) + 1);
            bamboosNeeded.put(Color.PINK, bamboosNeeded.get(Color.PINK) + 1);
        }

        return pandaObjectives;
    }
}
