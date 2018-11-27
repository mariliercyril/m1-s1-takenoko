package com.raccoon.takenoko.game;

import com.raccoon.takenoko.game.tiles.Color;
import com.raccoon.takenoko.game.tiles.Tile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("preSetBoard")
@Scope("prototype")
public class PreSetBoard extends HashBoard {

    public PreSetBoard() {
        super();
        randomFill();
    }

    private void randomFill() {
        /*
        Here we must be able to specify somehow constraints about how this board will be filled
        E.G. we can't have hard-coded the amount of bamboos of each colour
         */

        List<Tile> tiles = new ArrayList<>();

        /*
        For now we just fill a list with 10 tiles of each colour
        There is a bamboo of size 4 on each of them
         */
        for (int i = 0; i < 10; i++) {
            Tile newTile = new Tile(Color.GREEN);
            newTile.setBambooSize(4);
            tiles.add(newTile);
        }
        for (int i = 0; i < 10; i++) {
            Tile newTile = new Tile(Color.PINK);
            newTile.setBambooSize(4);
            tiles.add(newTile);
        }
        for (int i = 0; i < 10; i++) {
            Tile newTile = new Tile(Color.YELLOW);
            newTile.setBambooSize(4);
            tiles.add(newTile);
        }

        // We shuffle the list
        Collections.shuffle(tiles);

        // We put each tile on the first available position
        for(Tile tile : tiles) {
            this.set(this.getAvailablePositions().get(0), tile);
        }
    }

}
