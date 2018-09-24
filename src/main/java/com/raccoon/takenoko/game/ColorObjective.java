package com.raccoon.takenoko.game;

import java.awt.Point;

import java.util.List;

public class ColorObjective implements Objective {

	private boolean isCompleted;

	public ColorObjective() {

		isCompleted = false;
	}

	@Override
	public boolean isCompleted(Tile basicTile, Board hashBoard) {

		// Gets the position of the basic tile
		Point basicTilePosition = basicTile.getPosition();

		// Gets the tiles which are in the neighbourhood of the basic tile
		List<Tile> basicTileNeighbours = hashBoard.getNeighbours(basicTilePosition);

		// Is completed if the three tiles are aligned
		for (Tile secondTile : basicTileNeighbours) {
			// Gets the position of second tile
			Point secondTilePosition = secondTile.getPosition();
			// Gets the tiles which are in the neighbourhood of each tile which is in the neighbourhood of the basic tile
			List<Tile> secondTileNeighbours = hashBoard.getNeighbours(secondTilePosition);
			for (Tile thirdTile : secondTileNeighbours) {
				// Gets the position of third tile
				Point thirdTilePosition = thirdTile.getPosition();
				isCompleted = areAligned(basicTilePosition, secondTilePosition, thirdTilePosition, 1, 0)
						|| areAligned(basicTilePosition, secondTilePosition, thirdTilePosition, 1, -1)
						|| areAligned(basicTilePosition, secondTilePosition, thirdTilePosition, 0, 1);
			}
		}

		return isCompleted;
	}

	/**
	 * Returns true if three tiles are aligned.
	 * 
	 * @param firstPosition
	 *        The position of a first Tile
	 * @param secondPosition
	 *        The position of a Tile from the first Tile (of a neighbour Tile of the first Tile)
	 * @param thirdPosition
	 *        The position of a Tile from the second Tile (of a neighbour Tile of the second Tile)
	 * @param dx
	 *        The x of the translation vector
	 * @param dy
	 *        The y of the translation vector
	 * 
	 * @return If the three tiles in question are aligned
	 */
	private boolean areAligned(Point firstPosition, Point secondPosition, Point thirdPosition, int dx, int dy) {

		Point firstPoint = new Point(firstPosition);
		firstPoint.translate(dx, dy);

		Point secondPoint = new Point(secondPosition);
		secondPoint.translate(dx, dy);

		return (secondPosition.equals(firstPoint) && thirdPosition.equals(secondPoint));
	}

    // TODO: To define.
	@Override
	public int getScore() {

		return 0;
	}
}
