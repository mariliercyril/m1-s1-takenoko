package com.raccoon.takenoko.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashBoard implements Board {

    /*
     ******** Fields ********
     */
    private HashMap<Point, Tile> board;     // The actual representation of the board, which is an HashMap in this implementation
    private List<Tile> neighbouringTiles;   // the list of the tiles with a free border

    /*
     ******** Constructor ********
     */

    /**
     * Return a new HashBoard, initialized with a Tile in its center (0, 0).
     * The representation is hexagonal. Each horizontal line share a common 'y' component of its coordinates, each
     * diagonal from bottom left to top right share a common 'x' component of its coordinates, and each diagonal from
     * top left to bottom right has both its coordinates components evolving in the oposite direction (i.e. [-1, +1], or [+1, -1]).
     *
     * @param firstTile The Tile to put on the center of the board.
     */
    public HashBoard(Tile firstTile) {

        this.board = new HashMap<>();
        this.neighbouringTiles = new ArrayList<>();

        this.set(new Point(0,0), firstTile);
        firstTile.setPosition(new Point(0, 0));
        firstTile.setFreeBorders(6);

    }

    /*
     ******** Methods ********
     */


    @Override
    public Tile get(Point position) {
        // Simple translation of the HashMap get
        return board.get(position);
    }


    @Override
    public void set(Point position, Tile tile) {


        /*
        **********************************************************
        * This code is related to the neighbouringTiles list,
        * which is not maintained yet in this version
        **********************************************************

        List<Tile> neighbourgs = this.getNeighbours(position);

        for (Tile emptyNeighbourTile : neighbouringTiles) {
            if (neighbourgs.contains(emptyNeighbourTile)) {

                emptyNeighbourTile.setFreeBorders(emptyNeighbourTile.getFreeBorders() - 1);

                if (emptyNeighbourTile.getFreeBorders() <= 0) {
                    this.neighbouringTiles.remove(emptyNeighbourTile);
                }

            }
        }


        tile.setFreeBorders(4);     // CAUTION !!! Temporary, be careful has to be changed SOON !
        this.neighbouringTiles.add(tile);*/

        board.put(position, tile);      // We simply put the tile in the HashMap with the right key
        tile.setPosition(position);     // we indicate its coordinates to the tile

    }

    @Override
    public List<Point> getAvailablePositions() {

        ArrayList<Point> availablePositions = new ArrayList<>();

        Point tempPoint;    // We use this to remember each point we create, so when we are done looking
                            // for a position we still have it

        // The 'y' component of the coordinate
        int y = 0;

        // We start from the (0, 0) position, and we look in a straight line for the first position without a tile
        while(board.containsKey(tempPoint = new Point(0, y++)));

        // This position is added to the (partial) list of available positions
        availablePositions.add(tempPoint);

        // We return it
        return  availablePositions;
    }

    @Override
    public List<Tile> getNeighbours(Point position) {

        ArrayList<Tile> neighbours = new ArrayList<>();
        Point tempPoint;

        Point[] vectors = new Point[6];
        vectors[0] = new Point(-1,0);
        vectors[1] = new Point(-1,1);
        vectors[2] = new Point(0,-1);
        vectors[3] = new Point(0,1);
        vectors[4] = new Point(1,-1);
        vectors[5] = new Point(1,0);

        for (Point vector : vectors) {

            tempPoint = new Point(position.x + vector.x, position.y + vector.y);

            if (board.containsKey(tempPoint)) {
                neighbours.add(this.get(tempPoint));
            }

        }
        neighbours.remove(this.get(position));

        return neighbours;
    }
}
