package com.raccoon.takenoko.game.objective;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Game;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.player.Player;

public interface ObjectivePool {
    void setGame(Game game);

    Objective draw(ObjectiveType t);

    void notifyTilePut(Tile tile, Board b);

    void notifyStomachEmptied(Player player);

    void notifyBambooEaten(Board b, Player p);

    void notifyBambooGrowth(Board b);

    boolean isDeckEmpty(ObjectiveType t);

    ObjectiveType getObjectiveType(Objective objective);
}
