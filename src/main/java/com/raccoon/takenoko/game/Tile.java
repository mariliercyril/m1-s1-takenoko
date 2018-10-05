package com.raccoon.takenoko.game;

import com.raccoon.takenoko.tool.Vector;

import java.awt.Point;
import java.util.List;

/**
 * This interface provides methods for Tile.
 */
public interface Tile {

    int getBambooSize();
    void increaseBambooSize(int bambooSize);
    Point getPosition();
    void setPosition(Point position);
    Color getColor();

    List<Vector> getIrrigatedTowards ();
    boolean isIrrigated();
    void irrigate(Vector direction);

    void decreaseBambooSize();
}