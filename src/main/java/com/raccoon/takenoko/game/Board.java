package com.raccoon.takenoko.game;

import java.awt.*;
import java.util.List;

public interface Board {

    /**
     * Get the tile on a position.
     * @param position The position where to look for the Tile
     * @return The tile in this position
     */
    Tile get(Point position);

    /**
     * Put a Tile on the board on a specific position.
     * @param position Where to put it
     * @param tile The Tile to put
     */
    void set(Point position, Tile tile);

    /**
     * Analyse the board to look for all the positions where it is possible to put a Tile
     * @return The list of available positions
     */
    List<Point> getAvailablePositions();

    /**
     * Allows to acces all the Tiles neighbouring a position.
     * @param position The position around which to search
     * @return The list of Tile neighbouring this position
     */
    List<Tile> getNeighbours(Point position);

    /**
     * Returns the list of positions accessible on the board from the one given in parameters
     * @param initialPosition  The position to start looking from
     * @return  The list of positions accessible
     */
    List<Point> getAccessiblePositions(Point initialPosition);
}
