package com.raccoon.takenoko.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashBoard implements Board {

    private HashMap<Point, Tile> board;

    public HashBoard() {
        this.board = new HashMap<Point, Tile>();
    }

    @Override
    public Tile get(Point position) {
        return board.get(position);
    }

    @Override
    public void set(Point position, Tile tile) {
        board.put(position, tile);
    }

    @Override
    public List<Point> getAvailablePositions() {

        ArrayList<Point> availablePositions = new ArrayList<Point>();

        return  availablePositions;
    }

    @Override
    public List<Tile> getNeighbours(Point position) {

        ArrayList<Tile> neighbours = new ArrayList<Tile>();
        Point tempPoint;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                tempPoint = new Point(position.x + i, position.y + j);
                if (board.containsKey(tempPoint)) {
                    neighbours.add(this.get(tempPoint));
                }
            }
        }
        neighbours.remove(this.get(position));

        return neighbours;
    }
}
