package com.raccoon.takenoko.Player;

import com.raccoon.takenoko.Game.Board;
import com.raccoon.takenoko.Game.Game;
import com.raccoon.takenoko.Game.Tile;
import com.raccoon.takenoko.Takeyesntko;

import java.awt.*;
import java.util.List;
import java.util.Collections;
import java.util.Objects;

public class RandomBot extends Player {

    public RandomBot() {
        super();
    }

    /**
     * Puts down a tile at a random available location on the board given by the game
     *
     * @param game the game in which the player is playing
     */
    @Override
    protected void putDownTile(Game game, Tile t) {
        Board b = game.getBoard();
        if (Objects.isNull(b)) {
            return;
        }
        List availablePositions = b.getAvailablePositions();
        Collections.shuffle(availablePositions);
        Takeyesntko.print("There are " + availablePositions.size() + " available positions");

        Point playingPos;
        if (availablePositions.size() > 0) {
            playingPos = (Point) availablePositions.get(0);
            Takeyesntko.print("I will put down the tile at " + playingPos.toString());
            b.set(playingPos, t);
        }
    }
}
