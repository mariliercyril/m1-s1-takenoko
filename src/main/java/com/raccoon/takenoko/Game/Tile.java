package com.raccoon.takenoko.Game;

import java.awt.Point;
import java.util.List;

public interface Tile {

    public Point getPosition();
    public void setPosition(Point position);
    public int getFreeBorders();

}