package com.raccoon.takenoko;

import java.util.List;

public interface Board {

    /**
     *
     * @param i
     * @param j
     * @return The Tile at the coordinates i,j
     */
    public Tile get(int i, int j);

    /**
     * Put the Tile tile on the board at the coordinates i, j
     * @param i
     * @param j
     * @param tile
     */
    public void set(int i, int j, Tile tile);

    /**
     *  Method returning the available position to put a Tile on. Not yet clear about how
     *  to implement this list…
     * @return TODO
     */
    public List<Object> getAvailablePositions();

}
