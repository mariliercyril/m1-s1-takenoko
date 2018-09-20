package com.raccoon.takenoko;

import java.awt.*;
import java.util.List;

public interface Board {

    public Tile get(Point position);

    public void set(Point position, Tile tile);

    public List<Point> getAvailablePositions();

    public List<Tile> getNeighbours();

}
