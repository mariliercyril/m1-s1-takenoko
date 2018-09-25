package com.raccoon.takenoko.game;

import java.awt.Point;

/**
 * This class allows to define a basic tile.
 */
public class BasicTile implements Tile {

	private Point position;



    private int bambooSize;
	private Color color;

	public BasicTile() {

	    bambooSize = 0;

	}

	public BasicTile(Color color) {

		this();
		this.color = color;
	}

	public BasicTile(Point position) {

		this();
		this.position = position;
	}

	@Override
    public int getBambooSize() {
        return bambooSize;
    }

    @Override
    public void increaseBambooSize(int bambooSize) {
	    if (this.getBambooSize() < 4) {
	        this.bambooSize ++;
        }
    }

	@Override
	public Point getPosition() {

		return position;
	}

	@Override
	public void setPosition(Point position) {

		this.position = position;
	}

	@Override
	public Color getColor() {	// Returns the color of the tile
		return color;
	}
}
