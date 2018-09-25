package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;

import java.awt.*;
import java.util.*;
import java.util.List;

public class HashBoard implements Board {

    /*
     ******** Fields ********
     */
    private HashMap<Point, Tile> board;         // The actual representation of the board, which is an HashMap in this implementation
    private List<Point> availablePositions;     // the list of the tiles with a free border

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

        Point firstPosition = new Point(0, 0);

        this.availablePositions = new ArrayList<>(Arrays.asList(this.getNeighboursPrivate(firstPosition)));

        this.set(new Point(0, 0), firstTile);
        firstTile.setPosition(new Point(0, 0));

    }

    /*
     ******** Methods ********
     */

    /*
     **** Private ****
     */

    private Point[] getNeighboursPrivate(Point position) {
        /*
        Returns an array with the coordinates of all the neighbours position
        of the specified position
         */

        Point[] vectors = new Point[6];
        vectors[0] = new Point(position.x - 1, position.y);
        vectors[1] = new Point(position.x - 1, position.y + 1);
        vectors[2] = new Point(position.x, position.y - 1);
        vectors[3] = new Point(position.x, position.y + 1);
        vectors[4] = new Point(position.x + 1, position.y - 1);
        vectors[5] = new Point(position.x + 1, position.y);

        return vectors;

    }


    private List<Point> getFreeNeighbouringPositions(Point position) {
        /*
        Returns the free positions on the board adjacent to the given position
         */
        List<Point> neighboursAvailable = new ArrayList<>();

        Point[] neighbours = this.getNeighboursPrivate(position);

        for (Point point : neighbours) {

            if (!board.containsKey(point)) {
                neighboursAvailable.add(point);
            }

        }
        neighboursAvailable.remove(position);

        return neighboursAvailable;
    }

    /*
     **** Public ****
     */

    @Override
    public Tile get(Point position) {
        // Simple translation of the HashMap get
        return board.get(position);
    }


    @Override
    public void set(Point position, Tile tile) {

        this.availablePositions.remove(position);    // We remove our position from the list (will crash if this position was not available…)

        board.put(position, tile);      // We simply put the tile in the HashMap with the right key

        List<Point> neighbourPositions = this.getFreeNeighbouringPositions(position);   // We get the list of the free positions adjacent to one of the new tile

        for (Point emptyPosition : neighbourPositions) {        // For each empty position
            if (this.getNeighbours(emptyPosition).size() >= 2 && !availablePositions.contains(emptyPosition)) {
                this.availablePositions.add(emptyPosition);     // We add it to the available positions if 2 tiles at least are adjacent
                // and if it's not there yet
            }
        }

        tile.setPosition(position);     // we indicate its coordinates to the tile -- MAYBE USELESS ?--
        Takeyesntko.print("A tile has been placed at " + position + ".");

        if (!Objects.isNull(tile.getColor()) && tile.isIrrigated()) {
            tile.increaseBambooSize(1);
            Takeyesntko.print("A bamboo grew on the tile.");
        }

    }

    @Override
    public List<Point> getAvailablePositions() {

        return this.availablePositions;

    }

    @Override
    public List<Tile> getNeighbours(Point position) {

        ArrayList<Tile> neighbours = new ArrayList<>();

        Point[] points = this.getNeighboursPrivate(position);

        for (Point point : points) {

            if (board.containsKey(point)) {
                neighbours.add(this.get(point));
            }

        }
        neighbours.remove(this.get(position));

        return neighbours;
    }

}
