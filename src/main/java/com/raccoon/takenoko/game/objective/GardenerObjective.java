package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

import java.util.List;

public class GardenerObjective extends Objective {
    private int nbrToCValidate;
    private Color c;

    public GardenerObjective(int nbrToCValidate, Color c, int score) {
        this.nbrToCValidate = nbrToCValidate;
        this.score = score;
        this.c = c;
    }

    public GardenerObjective(int nbrToCValidate, Color c) {
        this.nbrToCValidate = nbrToCValidate;
        this.c = c;
        this.score = 1;
    }

    @Override
    public void checkIfCompleted(Board board) {
        List<Tile> tiles = board.getAllTiles();
        tiles.removeIf(t -> ( t.getColor() != c ) || ( t.getBambooSize() != nbrToCValidate ));

        this.isCompleted = !tiles.isEmpty();
    }
}
