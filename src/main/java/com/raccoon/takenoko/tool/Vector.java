package com.raccoon.takenoko.tool;

import java.awt.Point;

/**
 * This class allows to define a vector (as a Point) and several operations (such as the sum).
 */
public class Vector extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	public Vector(int x, int y) {

		super(x, y);
	}

	/**
	 * Performs the sum of (two) vectors.
	 * 
	 * @param v1
	 *        A first vector
	 * @param v2
	 *        A second vector
	 * 
	 * @return The sum vector
	 */
	public static Vector sum(Vector v1, Vector v2) {

		return new Vector(v1.x + v2.x, v1.y + v2.y);
	}

	/**
	 * Allows to get the opposite of a vector.
	 * 
	 * @param v
	 *        A vector
	 * 
	 * @return The opposite vector
	 */
	public Vector opposite(Vector v) {

		return new Vector(-v.x, -v.y);
	}
}
