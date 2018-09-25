package com.raccoon.takenoko.tool;

import java.awt.Point;

/**
 * This class allows to define a vector (as a Point) and several operations (such as the opposite).
 */
public final class Vector extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// The unit Vector I and the unit Vector J
	public static final Vector I = new Vector(1, 0);
	public static final Vector J = new Vector(0, 1);

	// A third Vector to facilitate the location of objects on the board (Tile, etc.)
	// (Can get it with the unit vectors!)
	public static final Vector K = new Vector(1, 1);

	private int x;
	private int y;

	public Vector(int x, int y) {

		super(x, y);
	}

	/**
	 * Applies a Vector on a Point.
	 * 
	 * @param point
	 *        A point for applying
	 * 
	 * @return The point by the translation
	 */
	public Point apply(Point point) {

		return new Point(point.x + this.x, point.y + this.y);
	}

	/**
	 * Allows to get the opposite of a vector.
	 * 
	 * @param v
	 *        A vector
	 * 
	 * @return The opposite vector
	 */
	public Vector opposite(Vector vector) {

		return new Vector(-vector.x, -vector.y);
	}

}
