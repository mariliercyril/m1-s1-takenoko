package com.raccoon.takenoko.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashBoard implements Board {

    private HashMap<Point, Tile> board;
    private List<Tile> neighbouringTiles;

    public HashBoard(Tile firstTile) {

        this.board = new HashMap<Point, Tile>();
        this.neighbouringTiles = new ArrayList<Tile>();

        this.set(new Point(0,0), firstTile);
        firstTile.setPosition(new Point(0, 0));
        firstTile.setFreeBorders(6);

    }

    @Override
    public Tile get(Point position) {
        return board.get(position);
    }

    @Override
    public void set(Point position, Tile tile) {

        List<Tile> neighbourgs = this.getNeighbours(position);

        for (Tile emptyNeighbourTile : neighbouringTiles) {
            if (neighbourgs.contains(emptyNeighbourTile)) {

                emptyNeighbourTile.setFreeBorders(emptyNeighbourTile.getFreeBorders() - 1);

                if (emptyNeighbourTile.getFreeBorders() <= 0) {
                    this.neighbouringTiles.remove(emptyNeighbourTile);
                }

            }
        }

        board.put(position, tile);
        tile.setFreeBorders(4);     // CAUTION !!! Temporary, be careful has to be changed SOON !
        this.neighbouringTiles.add(tile);
        tile.setPosition(position);


    }

    @Override
    public List<Point> getAvailablePositions() {

        ArrayList<Point> availablePositions = new ArrayList<Point>();

        Point[] vectors = new Point[6];
        vectors[0] = new Point(-1,0);
        vectors[1] = new Point(-1,1);
        vectors[2] = new Point(0,-1);
        vectors[3] = new Point(0,1);
        vectors[4] = new Point(1,-1);
        vectors[5] = new Point(1,0);

        Point tempPoint;

        for (Tile tile : neighbouringTiles) {
            for (Point vector : vectors) {

                tempPoint = new Point(tile.getPosition().x + vector.x, tile.getPosition().y + vector.y);

                if (!board.containsKey(tempPoint)) {
                    availablePositions.add(tempPoint);
                }

            }
        }

        return  availablePositions;
    }

    @Override
    public List<Tile> getNeighbours(Point position) {

        ArrayList<Tile> neighbours = new ArrayList<Tile>();
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
