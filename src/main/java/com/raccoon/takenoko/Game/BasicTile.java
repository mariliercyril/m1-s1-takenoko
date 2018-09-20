package com.raccoon.takenoko.Game;

import java.awt.Point;

import java.util.List;

public class BasicTile implements Tile {

	public static final int BORDERS_MAXIMUM = 6;

	private Point position;

	BasicTile(Point position) {

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

	//@Override
	public int getFreeBorders(Board hashBoard) {

		// Gets the tiles which are in the neighbourhood of the tile to be placed
		List<Tile> tiles = hashBoard.getNeighbours(position);

		return (BORDERS_MAXIMUM - tiles.size());
	}

	// TODO: To remove the following version of the method "getFreeBorders".
	@Override
	public int getFreeBorders() {

		return 0;
	}

	@Override
	public void setFreeBorders(int numberOfFreeNumbers) {
		// TODO Auto-generated method stub

	}
}

