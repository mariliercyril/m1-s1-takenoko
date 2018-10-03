package com.raccoon.takenoko.game;

import java.awt.Point;

/**
 * This interface provides methods for Tile.
 */
public interface Tile {

    int getBambooSize();
    void increaseBambooSize(int bambooSize);
    Point getPosition();
    void setPosition(Point position);
    Color getColor();
    public boolean isIrrigated();
    public void irrigate();

    void decreaseBambooSize();
}