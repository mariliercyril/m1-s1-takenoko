package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.game.tiles.IrrigationState;
import com.raccoon.takenoko.game.tiles.Tile;
import com.raccoon.takenoko.tool.UnitVector;
import com.raccoon.takenoko.tool.Vector;

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
     * Constructs a new HashBoard, initialized with a Tile in its center (0, 0).
     * The representation is hexagonal. Each horizontal line share a common 'y' component of its coordinates, each
     * diagonal from bottom left to top right share a common 'x' component of its coordinates, and each diagonal from
     * top left to bottom right has both its coordinates components evolving in the oposite direction (i.e. [-1, +1], or [+1, -1]).
     *
     * @param firstTile The Tile to put on the center of the board.
     */
    public HashBoard(Tile firstTile) {

        this.board = new HashMap<>();

        Point firstPosition = new Point(0, 0);

        this.availablePositions = new ArrayList<>(Arrays.asList(this.getNeighbouringCoordinates(firstPosition)));

        this.set(new Point(0, 0), firstTile);
        firstTile.setPosition(new Point(0, 0));
    }

    /*
     ******** Methods ********
     */

    /*
     **** Private ****
     */

    private Point[] getNeighbouringCoordinates(Point position) {
        /*
        Returns an array with the coordinates of all the neighbours position
        of the specified position
         */

        Point[] tab = new Point[6];




        Point[] vectors = new Point[6];
        vectors[0] = new Point(position.x - 1, position.y);
        vectors[1] = new Point(position.x - 1, position.y - 1);
        vectors[2] = new Point(position.x, position.y - 1);
        vectors[3] = new Point(position.x, position.y + 1);
        vectors[4] = new Point(position.x + 1, position.y + 1);
        vectors[5] = new Point(position.x + 1, position.y);

        return vectors;

    }


    private List<Point> getFreeNeighbouringPositions(Point position) {
        /*
        Returns the free positions on the board adjacent to the given position
         */
        List<Point> neighboursAvailable = new ArrayList<>();

        Point[] neighbours = this.getNeighbouringCoordinates(position);

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

        /*
        ********************************************************
        * All this code just to treat the case of the irrigation
        * of the tiles next to the pond.
        * This is the initialisation of the conditions to build a coherent
        * irrigation network.
         */
        if (Arrays.asList(getNeighbouringCoordinates(position)).contains(new Point(0, 0))) {

            UnitVector directionOfThePond = null;
            /* We look for the UnitVector pointing toward the pond from our tile
            As we don't have a way to get a UnitVector from a corresponding vector.
            */
            for (int i = 0; i < UnitVector.values().length; i++) {
                if (UnitVector.values()[i].getVector().equals((new Vector(position)).getOpposite())) {
                    directionOfThePond = UnitVector.values()[i];
                }
            }

            // We put an irrigation chanel on the border adjacent to the pond
            tile.irrigate(directionOfThePond);

            // then we gather the vectors from our tile to our potential neighbours
            UnitVector firstNeighbourDirection = directionOfThePond.rotation(-1);
            UnitVector secondNeighbourDirection = directionOfThePond.rotation(1);

            if (board.containsKey(firstNeighbourDirection.getVector().applyTo(position))) {
                // If we have a neighbour there
                // we tell our tile to be irrigable on this border
                tile.setIrrigable(firstNeighbourDirection);
                // and to the adjacent tile as well
                board.get(firstNeighbourDirection.getVector().applyTo(position)).setIrrigable(firstNeighbourDirection.opposite());
            }
            if (board.containsKey(secondNeighbourDirection.getVector().applyTo(position))) {
                // If we have a neighbour there
                // we tell our tile to be irrigable on this border
                tile.setIrrigable(secondNeighbourDirection);
                // and to the adjacent tile as well
                board.get(secondNeighbourDirection.getVector().applyTo(position)).setIrrigable(secondNeighbourDirection.opposite());
            }
        }

        // We maintain the list of available positions
        for (Point emptyPosition : neighbourPositions) {        // For each empty position adjacent to a tile
            if (this.getNeighbours(emptyPosition).size() >= 2 && !availablePositions.contains(emptyPosition)) {
                // If 2 tiles at least are adjacent and if it's not there yet
                this.availablePositions.add(emptyPosition);     // we add it to the available positions
            }
        }

        tile.setPosition(position);     // we indicate its coordinates to the tile
        Takeyesntko.print("A tile has been placed at " + position + ".");
    }

    @Override
    public List<Point> getAvailablePositions() {
        return this.availablePositions;
    }

    @Override
    public List<Tile> getNeighbours(Point position) {

        ArrayList<Tile> neighbours = new ArrayList<>();

        Point[] points = this.getNeighbouringCoordinates(position);

        for (Point point : points) {

            if (board.containsKey(point)) {
                neighbours.add(this.get(point));
            }

        }
        neighbours.remove(this.get(position));

        return neighbours;
    }

    @Override
    public List<Point> getAccessiblePositions(Point initialPosition) {

        ArrayList<Point> accessiblePositions = new ArrayList<>();   // Instantiation of the empty list

        for (UnitVector unitVector : UnitVector.values()) {
            Point tempPoint = initialPosition;      // tempPoint will travel to every position accessible in straight line
            // using the UNIT vectors.

            while (this.board.containsKey(tempPoint = unitVector.getVector().applyTo(tempPoint))) {
                accessiblePositions.add(tempPoint);
            }
        }

        return accessiblePositions;
    }

    @Override
    public boolean irrigate(Point p, UnitVector direction) {

        // Get the coordinates of the second tile involved in this irrigation
        Point otherPositionToIrrigate = new Point(direction.getVector().applyTo(p));

        if (board.containsKey(otherPositionToIrrigate) && this.get(p).getIrrigationState(direction).equals(IrrigationState.IRRIGABLE)) {
            // If the position actually consists in two tiles adjacent connected to the irrigation network
            //TODO : Replace the canIrrigate with a check of the irrigationState
            this.get(p).irrigate(direction);    // We irrigate the tile
            this.get(otherPositionToIrrigate).irrigate(direction.opposite());   // and the adjacent one

            /*
            **********************************************************************************
            DANGER ZONE : I'll comment, refactor and explain this later. Pontential source of bug.
            Variables have to have a better name
             */

            int[] angles = {-1, 1};

            for (int angle : angles) {
                UnitVector directionVector = direction.rotation(angle);
                Point irrigablePosition = directionVector.getVector().applyTo(p);
                if(board.containsKey(irrigablePosition)) {
                    // If we have a tile that becomes irrigable
                    Tile irrigableTile = board.get(irrigablePosition);

                    this.get(p).setIrrigable(directionVector);
                    this.get(otherPositionToIrrigate).setIrrigable(directionVector.rotation(angle));
                    irrigableTile.setIrrigable(directionVector.opposite());
                    irrigableTile.setIrrigable(directionVector.opposite().rotation(angle));
                }
            }

            /*
            END OF THE DANGER ZONE
            ***************************************************
             */

            return true;    // Means the irrigation procedure worked
        }

        return false;
    }

    @Override
    public List<Tile> getAllTiles() {
        return new ArrayList<>(board.values());
    }

    @Override
    public boolean canIrrigate(Point p, UnitVector direction) {

        Tile t = this.get(p);
        Tile tNext = this.get(direction.getVector().applyTo(p));

        // We must put down the irrigation between two tiles
        if (Objects.isNull(tNext)) { return false; }

        List<UnitVector> l = t.getIrrigatedTowards();
        List<UnitVector> lNext = tNext.getIrrigatedTowards();

        return l.contains(direction.rotation(1))            // current tile's direction's left border
                || l.contains(direction.rotation(-1))       // current tile's direction's right border
                || lNext.contains(direction.rotation(1))    // next tile's direction's left border
                || lNext.contains(direction.rotation(-1));  // next tile's direction's right border
    }


}
