package com.raccoon.takenoko.game.objective;

import java.awt.Point;

import java.util.Arrays;
import java.util.List;

import com.raccoon.takenoko.game.Board;
import com.raccoon.takenoko.game.Color;
import com.raccoon.takenoko.game.Tile;

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
	public boolean isCompleted() {

		return isCompleted;
	}

	@Override
	public boolean checkIfCompleted(Tile basicTile, Board hashBoard) {

		Point[] positions = new Point[3];

		/*
		 * THE ALIGNMENT
		 */
		// Gets the position of the basic Tile
		positions[0] = basicTile.getPosition();
		// Gets the neighbours of the basic Tile
		List<Tile> basicTileNeighboursList = hashBoard.getNeighbours(positions[0]);
		// Converts the List into array
		Tile[] basicTileNeighbours = new Tile[basicTileNeighboursList.size()];
		basicTileNeighbours = basicTileNeighboursList.toArray(basicTileNeighbours);
		for (int i = 0; !isCompleted && i < basicTileNeighbours.length; i++) {
			// Gets the position of neighbour of the basic Tile
			positions[1] = basicTileNeighbours[i].getPosition();
			// Gets the translation Vector
			Vector translation = new Vector(positions[0], positions[1]);
			// Parses the neighbours of the basic Tile again
			for (int j = 0; j < basicTileNeighbours.length; j++) {
				Point temporaryPosition = basicTileNeighbours[j].getPosition();

				// Checking...
				if (j != i) {
					if (new Vector(temporaryPosition, positions[0]).equals(translation)) {
						positions[2] = temporaryPosition;
						isCompleted = true;
					}
				}
			}
			if (!isCompleted) {
				// Gets the neighbours of each neighbour of the basic Tile
				List<Tile> secondTileNeighboursList = hashBoard.getNeighbours(positions[1]);
				// Parses the neighbours of each neighbour of the basic Tile
				for (Tile thirdTile : secondTileNeighboursList) {
					Point temporaryPosition = thirdTile.getPosition();

					// Checking...
					if (new Vector(positions[1], temporaryPosition).equals(translation)) {
						positions[2] = temporaryPosition;
						isCompleted = true;
					}
				}
			}
		}

		/*
		 * THE COLOR (if alignment is completed)
		 */
		if (isCompleted && !((Arrays.asList(positions)).contains(null))) {
			Color[] colors = new Color[3];
			// Gets the color of each Tile
			for (int i = 0; i < 3; i++) {
				colors[i] = (hashBoard.get(positions[i])).getColor();
			}

			// Checking...
			if (!((Arrays.asList(colors)).contains(null))) {
				isCompleted = colors[1].equals(colors[0]) && colors[2].equals(colors[1]);
			}
		}

		return isCompleted;
	}

    // TODO: To define.
	@Override
	public int getScore() {

		return 0;
	}

}
