package com.raccoon.takenoko.Game;

import java.awt.Point;

/**
 * This class allows to define a basic (first) tile.
 */
public class BasicTile implements Tile {

	private Point position;
	private int numberOfFreeNumbers;

	BasicTile(Point position) {

		numberOfFreeNumbers = 0;
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
}
