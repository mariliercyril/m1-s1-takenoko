package com.raccoon.takenoko.game;

import java.awt.Point;

/**
 * This interface provides methods for Tile.
 */
public interface Tile {

    public Point getPosition();
    public void setPosition(Point position);
    public Color getColor();
}