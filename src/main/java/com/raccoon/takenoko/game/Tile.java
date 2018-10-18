package com.raccoon.takenoko.game;

import com.raccoon.takenoko.Takeyesntko;
import com.raccoon.takenoko.tool.UnitVector;
import com.raccoon.takenoko.tool.Vector;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Representation of the tiles of the game.
 */
public class Tile {

    private Point position;

    private int bambooSize;
    private Color color;
    private boolean irrigated;

    private List<Vector> irrigatedTowards;

    /**
     * Constructs a "pond" tile, that is to say the first tile to put on the board with specifics properties.
     */
    public Tile() {
        irrigatedTowards = new ArrayList<>(Arrays.asList(UnitVector.getVectors()));
        this.irrigated = false;
        this.color = null;
        position = new Point(0,0);

    }

    /**
     * Constructs a new tile of the specified color
     */
    public Tile(Color color) {

        this.color = color;
        bambooSize = 0;
        irrigatedTowards = new ArrayList<>();
        this.irrigated = false;
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
        if (!this.irrigated) {
            this.irrigated = true;
            this.increaseBambooSize(1);
        }

        if (!irrigatedTowards.contains(direction)) {
            this.irrigatedTowards.add(direction);
        }
    }

    public void decreaseBambooSize() {
        if (this.bambooSize > 0) {
            this.bambooSize--;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
