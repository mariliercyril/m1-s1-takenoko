package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;
import com.raccoon.takenoko.tool.Constants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Scope("prototype")
public class PandaOnlyObjPool extends ObjectivePool {

    public PandaOnlyObjPool() {
        super();
        List<PandaObjective> pandaObjectives = getPandaObjectives();

        Collections.shuffle(pandaObjectives);

        this.bambooObjectives.addAll(pandaObjectives);
        this.deck.get(ObjectiveType.PANDA).addAll(this.bambooObjectives);

    }

    private List<PandaObjective> getPandaObjectives() {
        ArrayList<PandaObjective> pandaObjectives = new ArrayList<>();

        for (int i = 0; i < Constants.NUMBER_OF_2_GREEN_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(2,0,0,3));
        }

        for (int i = 0; i < Constants.NUMBER_OF_2_YELLOW_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(0,2,0,4));
        }

        for (int i = 0; i < Constants.NUMBER_OF_2_PINK_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(0,0,2, 5));
        }

        for (int i = 0; i < Constants.NUMBER_OF_3_CHUNKS_PANDA_OBJECTIVE; i++) {
            pandaObjectives.add(new PandaObjective(1,1,1, 6));
        }

        return pandaObjectives;
    }
}
