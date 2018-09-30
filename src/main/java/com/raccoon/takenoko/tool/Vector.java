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

	public Vector(int x, int y) {

		super(x, y);
	}

	public Vector(Point startPoint, Point endPoint) {

		super(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
	}

	/**
	 * Applies a vector on a point.
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
	 * Gets the opposite of a vector.
	 * 
	 * @return The opposite Vector
	 */
	public Vector getOpposite() {

		return new Vector(-this.x, -this.y);
	}

	/**
	 * Gets the sum of vectors.
	 * 
	 * @return The sum Vector
	 */
	public static Vector sum(Vector... vectors) {

		int x = 0;
		int y = 0;

		for (Vector vector : vectors) {
			x += vector.x;
			y += vector.y;
		}

		return new Vector(x, y);
	}

}
