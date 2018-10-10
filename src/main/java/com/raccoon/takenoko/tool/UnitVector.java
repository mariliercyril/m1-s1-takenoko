package com.raccoon.takenoko.tool;

/**
 * An unit vector, such as '<i>i</i>'.
 * <p>{@code UnitVector} is an enum representing above all the two unit vectors - <i>i</i> and <i>j</i> - of a 2-dimensional space.
 * Each of these two vectors is defined by a {@link Vector} provided as a parameter.</p>
 *  
 * <p>(A third unit vector - <i>k</i>, defined by the sum of <i>i</i> and <i>j</i> - completes this enum.)</p>
 */
public enum UnitVector {

	/**
	 * The singleton instance for the unit vector <i>i</i>.
	 */
	I(new Vector(1, 0)),
	/**
	 * The singleton instance for the unit vector <i>j</i>.
	 */
	J(new Vector(0, 1)),

	/**
	 * The singleton instance for the unit vector <i>k</i>.
	 */
	K(Vector.sum(I.getVector(), J.getVector()));

	private final Vector vector;

	private UnitVector(Vector vector) {

		this.vector = vector;
	}

	/**
	 * Returns the vector as a {@code Vector} object.
	 * 
	 * @return the vector as a <i>Vector</i> object.
	 */
	public Vector getVector() {

		return vector;
	}

	/**
	 * Returns six unit vectors (<i>i</i>, <i>j</i>, and <i>k</i>, and their opposites),
	 * ordered in the trigonometric (anticlockwise) sense, for facilitating the tile position operations.
	 * Each of these six vectors could be used to point, in a unique way, to one of the six sides of a tile
	 * of which the coordinates define an application common point.
	 * 
	 * <p>Note that this method is often to be preferred to the {@code values()} (implicit) method.</p>
	 * 
	 * @return
	 *  the six unit vectors in the trigonometric sense.
	 */
	public static Vector[] getVectors() {

		return new Vector[]{
				I.getVector(),
				K.getVector(),
				J.getVector(),
				I.getVector().getOpposite(),
				K.getVector().getOpposite(),
				J.getVector().getOpposite()
		};
	}

}
