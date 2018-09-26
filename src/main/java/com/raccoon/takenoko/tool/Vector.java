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

	// The unit Vector I and the unit Vector J (we can get the third Vector with the vectors I and J)
	public static final Vector[] UNITS = { new Vector(1, 0), new Vector(0, 1), new Vector(1, 1) };

	public Vector(int x, int y) {

		super(x, y);
	}

	public Vector(Point startPoint, Point endPoint) {

		super(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
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
	 * @return The opposite vector
	 */
	public Vector opposite() {

		return new Vector(-this.x, -this.y);
	}

}
