package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.tool.Vector;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class allows to define a basic tile.
 */
public class Tile {

    private Point position;


    private int bambooSize;
    private Color color;
    private boolean irrigated;

    private List<Vector> irrigatedTowards;

    public Tile() {

        irrigated = true;    // todo : change to false when the players have to irrigate the tiles themselves
        bambooSize = 0;
        irrigatedTowards = new ArrayList<>();
    }

    public Tile(Color color) {

        this();
        this.color = color;
    }

    public Tile(Point position) {

        this();
        this.position = position;
    }

    public List<Vector> getIrrigatedTowards() {
        return irrigatedTowards;
    }

    public int getBambooSize() {
        return bambooSize;
    }

    // TODO: To remove the parameter (in the method "increaseBambooSize()") or use it.
    public void increaseBambooSize(int bambooSize) {
        if (this.getBambooSize() < 4 && this.irrigated && Objects.nonNull(this.color)) {
            this.bambooSize++;
            Takeyesntko.print("Bamboo on " + this.toString() + " increases to " + this.bambooSize + " chunks.");
        }
    }

    public Point getPosition() {

        return position;
    }

    public void setPosition(Point position) {

        this.position = position;
    }

    public Color getColor() {    // Returns the color of the tile
        return color;
    }

    public String toString() {
        return "Tile " + this.getColor() + " at " + this.getPosition();
    }

    public boolean isIrrigated() {
        return irrigated;
    }

    public void irrigate(Vector direction) {
        this.irrigated = true;

        if (!irrigatedTowards.contains(direction)) {
            this.irrigatedTowards.add(direction);
        }
    }

    public void decreaseBambooSize() {
        if (this.bambooSize > 0) {
            this.bambooSize--;
        }
    }
}
