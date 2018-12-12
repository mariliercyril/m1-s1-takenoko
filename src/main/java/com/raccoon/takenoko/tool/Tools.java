package com.raccoon.takenoko.tool;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.game.tiles.Color;
import java.awt.*;
import java.util.Map;

public final class Tools {

    private Tools() {

    }

    /**
     *
     * @param map A map that associates keys of some type to <b>positive</b> integers
     * @param <T> The key type
     * @return
     */
    public static <T> T mapMaxKey(Map<T, Integer> map) {

        T maxKey = (T)map.keySet().toArray()[0];
        int maxValue = map.get(maxKey);

        for (T key : map.keySet()) {
            if (map.get(key) >= maxValue) {
                maxValue = map.get(key);
                maxKey = key;
            }
        }

        return maxKey;
    }

    public static void parseLine(String line, Board board) {
        String[] components = line.split("\t");
        String code = components[0];
        Tile tile;
        Point location;
        int bambooSize;
        if (code.equals("TIL")) {
            bambooSize = Integer.parseInt(components[5]);
            if (components[3].equals("BLUE")) {
                tile = new Tile();
            } else {
                tile = new Tile(Color.valueOf(Color.class, components[3]));
            }
            location = new Point((int)Double.parseDouble(components[1]), (int)Double.parseDouble(components[2]));
            tile.setBambooSize(bambooSize);
            tile.setPosition(location);
            board.set(location, tile);
        }
    }
}
