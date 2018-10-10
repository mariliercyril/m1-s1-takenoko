package com.raccoon.takenoko.tool;

import java.awt.Point;

/**
 * The {@code Vector} class defines any vector of a 2-dimensional space.
 * <p>
 * In addition to defining a vector, this class provides methods for the vectors, such as:<ul>
 * <li>{@code apply(Point)}</li>
 * <li>{@code getOpposite()}</li>
 * <li>{@code sum(Vector...)}</li>
 * </ul>
 */
public final class Vector extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a location vector of a 2-dimensional space
	 * with coordinates (specified as parameters).
	 * 
	 * @param x
	 * 	the X coordinate of the point of which the vector is the location vector
	 * @param y
	 *  the Y coordinate of the point of which the vector is the location vector
	 */
	public Vector(int x, int y) {

		super(x, y);
	}

	/**
	 * Constructs a location vector of a 2-dimensional space
	 * with a point (specified as a parameter).
	 * 
	 * @param point
	 * 	the point of which the vector is the location vector
	 */
	public Vector(Point point) {

		this(point.x, point.y);
	}

	/**
	 * Constructs a vector of a 2-dimensional space
	 * with a start point and an end point (specified as parameters).
	 * 
	 * @param startPoint
	 *  the start point of the vector which we would to construct
	 * @param endPoint
	 *  the end point of the vector which we would to construct
	 */
	public Vector(Point startPoint, Point endPoint) {

		this(endPoint.x - startPoint.x, endPoint.y - startPoint.y);
	}

	/**
	 * Returns the point resulting from the application of a vector to a point.
	 * 
	 * @param point
	 *  the point of application
	 * 
	 * @return the point resulting from the application of a vector to a point
	 */
	public Point apply(Point point) {

		return new Point(point.x + this.x, point.y + this.y);
	}

	/**
	 * Returns the opposite of a vector.
	 * 
	 * @return the opposite vector
	 */
	public Vector getOpposite() {

		return new Vector(-this.x, -this.y);
	}

	/**
	 * Returns the sum of vectors.
	 * 
	 * @param vectors
	 * 	the vectors we would to add
	 * 
	 * @return the sum vector
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

	/**
	 * Returns the rotation of a vector, <i>v</i>.
	 * 
	 * @param angle
	 *  the angle as the number of rotations, <i>n</i>, to be done
	 *  for getting the <i>n</i>-th vector in the clockwise sense
	 *  as well as the trigonometric sense from <i>v</i>
	 * 
	 * @return the vector resulting from the rotation
	 */
	public Vector rotation(int angle) {

		Vector[] unitVectors = UnitVector.getVectors();

		for (int i = 0; i < unitVectors.length; i++) {
			if (this.equals(unitVectors[i])) {
				return unitVectors[(i + ((angle < 0) ? 6 : 0) + (angle % 6)) % 6];
			}
		}

		return this;
	}

}
