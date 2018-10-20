package com.raccoon.takenoko.game.tiles;

import com.raccoon.takenoko.tool.Constants;
import com.raccoon.takenoko.tool.UnitVector;

import java.awt.Point;
import java.util.*;

/**
 * Representation of the tiles of the game.
 */
public class Tile {

    /*
     *************************************************
     *                 Fields
     *************************************************
     */

    private Point position;

    private int bambooSize;
    private boolean irrigated;
    private Color color;

    private List<UnitVector> irrigatedTowards;

    private Map<UnitVector, IrrigationState> sideIrrigationState;

    /*
     *************************************************
     *                 Constructors
     *************************************************
     */

    /**
     * Constructs a "pond" tile, that is to say the first tile to put on the board with specifics properties.
     */
    public Tile() {
        irrigatedTowards = new ArrayList<>();
        for (UnitVector unitVector : UnitVector.values()) {
        	irrigatedTowards.add(unitVector);
        }
        this.irrigated = false;
        this.color = null;
        position = new Point(0,0);
        initializeSideIrrigation();
    }

    /**
     * Constructs a new tile of the specified color
     */
    public Tile(Color color) {

        this.color = color;
        bambooSize = 0;
        irrigatedTowards = new ArrayList<>();
        this.irrigated = false;
        initializeSideIrrigation();

    }

    private void initializeSideIrrigation() {

        this.sideIrrigationState = new EnumMap<>(UnitVector.class);
        for (UnitVector vector : UnitVector.values()) {
            sideIrrigationState.put(vector, IrrigationState.NOT_IRRIGATED);
        }
    }

    /*
     *************************************************
     *                 Gets / Sets
     *************************************************
     */

    public int getBambooSize() {
        return bambooSize;
    }

    public Point getPosition() {
        return position;
    }

    /**
     * @return the color of the tile
     */
    public Color getColor() {    // Returns the color of the tile
        return color;
    }

    /**
     * @return the irrigation state of the tile
     */
    public boolean isIrrigated() {
        return irrigated;
    }

    /**
     * Allows to set the position of the tile, useful when the tile is put down
     * @param position a point which will be the position of the Tile
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /*
     *************************************************
     *                 Methods
     *************************************************
     */

    /**
     * Allows to know the state of a tile border regarding its irrigation.
     * @param direction the UnitVector pointing to the border we want to check
     * @return the irrigation state
     */
    public IrrigationState getIrrigationState(UnitVector direction) {
        return this.sideIrrigationState.get(direction);
    }

    public List<UnitVector> getIrrigatedTowards() {
        //TODO : replace its usages by getIrrigationState
        return irrigatedTowards;
    }

    /**
     * Irrigate the tile : the tile is now irrigated, knows where the irrigation canal is.
     * when called on a {@code Tile} that wasn't irrigated yet, the bamboo grows. If called on an already irrigated
     * {@code Tile} just remember where the irrigation canal is.
     * @param direction a element from UnitVector, indicating the side to put the canal on.
     */
    public void irrigate(UnitVector direction) {
        //TODO : store the side irrigation state in the new map

        if (!this.irrigated) {  // If we are not yet irrigated
            this.irrigated = true;  // we become irrigated
            // and a bamboo grows
            this.increaseBambooSize(Constants.USUAL_BAMBOO_GROWTH);
        }

        // Whether we are irrigated or not, we remember the presence of a new canal :

        if (!irrigatedTowards.contains(direction)) {
            this.irrigatedTowards.add(direction);
        }

        this.sideIrrigationState.put(direction, IrrigationState.IRRIGATED);

    }

    /**
     * Set the irrigable state of a {@code Tile}. Doesn't do anything if the side is already irrigable or irrigated.
     * @param direction the UnitVector pointing toward the side to set irrigable.
     */
    public void setIrrigable(UnitVector direction) {
        /*TODO : Use of a single UnitVector to set irrigable both the sides ?
        Could be done if the direction is a reference to a point rather than a side…
        */
        if (this.sideIrrigationState.get(direction).equals(IrrigationState.NOT_IRRIGATED)) {
            this.sideIrrigationState.put(direction, IrrigationState.IRRIGABLE);
        }
    }


    /**
     * Increase the size of the bamboo on the tile. We make sure after increasing the the size doesn't exceed the max size.
     * @param bambooSize the size to add to the bamboo
     */
    public void increaseBambooSize(int bambooSize) {

        if(this.irrigated && Objects.nonNull(this.color)) {
            this.bambooSize += bambooSize;
        }

        if (this.getBambooSize() > Constants.MAX_BAMBOO_SIZE && this.irrigated ) {
            this.bambooSize = Constants.MAX_BAMBOO_SIZE;
        }
    }


    /**
     * Remove on chunk of bamboo on the {@code Tile}
     */
    public void decreaseBambooSize() {
        if (this.bambooSize > 0) {
            this.bambooSize--;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Tile " + this.getColor() + " at " + this.getPosition();
    }
}
