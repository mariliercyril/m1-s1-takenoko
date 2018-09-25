package com.raccoon.takenoko.game;

import java.awt.Point;

/**
 * This class allows to define a basic tile.
 */
public class BasicTile implements Tile {

	private Point position;

	private Color color;

	public BasicTile() {

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
	public Color getColor() {	// Returns the color of the tile
		return color;
	}
}
