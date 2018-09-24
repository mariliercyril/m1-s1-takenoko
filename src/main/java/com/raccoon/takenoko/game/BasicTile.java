package com.raccoon.takenoko.game;

import java.awt.Point;

/**
 * This class allows to define a basic (first) tile.
 */
public class BasicTile implements Tile {

	private Point position;
	private int numberOfFreeNumbers;
	private Color color;

	public BasicTile() {

		numberOfFreeNumbers = 0;
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
	public Point getPosition() {

		return position;
	}

	@Override
	public void setPosition(Point position) {

		this.position = position;
	}

	@Override
	public int getFreeBorders() {

		return numberOfFreeNumbers;
	}

	@Override
	public void setFreeBorders(int numberOfFreeNumbers) {

		this.numberOfFreeNumbers = numberOfFreeNumbers;
	}

	public Color getColor() {	// Returns the color of the tile
		return color;
	}
}
