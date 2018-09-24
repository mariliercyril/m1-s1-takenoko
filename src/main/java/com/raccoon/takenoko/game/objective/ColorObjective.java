package com.raccoon.takenoko.game.objective;

import java.awt.Point;

import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;
import com.raccoon.takenoko.tool.UnitVector;
import com.raccoon.takenoko.tool.Vector;

/**
 * This class allows to satisfy the following objective:
 * Align three tiles of the same color.
 */
public class ColorObjective implements Objective {

	public Point[] translationVectors = { new Point(0,1), new Point(1,1), new Point(1,0) };

	private boolean isCompleted;

	public ColorObjective() {

		isCompleted = false;
	}

	@Override
	public boolean isCompleted(Tile basicTile, Board hashBoard) {

		Point[] positions = new Point[3];

		// Gets the position of the basic tile
		positions[0] = basicTile.getPosition();
		// Gets the neighbours of the basic tile
		List<Tile> basicTileNeighbours = hashBoard.getNeighbours(positions[0]);

		// Checking...
		for (Tile secondTile : basicTileNeighbours) {
			// Gets the position of second tile
			positions[1] = secondTile.getPosition();
			// Gets the neighbours of neighbour of the basic tile
			List<Tile> secondTileNeighbours = hashBoard.getNeighbours(positions[1]);
			for (Tile thirdTile : secondTileNeighbours) {
				// Gets the position of third tile
				positions[2] = thirdTile.getPosition();

				Point[] vectors = new Point[3];
/*
				vectors[0] = new Point(0, 1);
				vectors[1] = new Point(-1, 1);
				vectors[2] = new Point(-1, 0);
*/
				vectors[0] = (UnitVector.I).get();
				vectors[1] = (UnitVector.J).get();
				vectors[2] = Vector.sum((UnitVector.I).get(), (UnitVector.J).get());
				for (Point vector : vectors) {
					if (areAligned(positions, vector.x, vector.y)) {
						Color[] colors = new Color[3];
						for (int i = 0; i < 3; i++) {
							colors[i] = (hashBoard.get(positions[i])).getColor();
						}
						if ((colors[0] != null) && (colors[1] != null) && colors[2] != null) {
							isCompleted = colors[1].equals(colors[0]) && colors[2].equals(colors[1]);
						}
					}
				}
			}
		}

		return isCompleted;
	}

	/**
	 * Returns true if three tiles are aligned.
	 * 
	 * @param positions
	 *        Three positions
	 * @param dx
	 *        The x of the translation vector
	 * @param dy
	 *        The y of the translation vector
	 * 
	 * @return If the three tiles in question are aligned
	 */
	private boolean areAligned(Point[] positions, int dx, int dy) {

		Point firstPosition = positions[0];
		Point secondPosition = positions[1];
		Point thirdPosition = positions[2];

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
