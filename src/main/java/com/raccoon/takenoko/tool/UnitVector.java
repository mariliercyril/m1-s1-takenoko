package com.raccoon.takenoko.tool;

/**
 * An unit vector, such as '<i>i</i>'.
 * <p>{@code UnitVector} is an enum representing above all the necessary unit vectors -
 * <i>i</i>, <i>j</i>, <i>k</i>, <i>l</i>, <i>m</i> and <i>n</i> - in a 2-dimensional space.
 * Each of these two vectors is defined by a {@link Vector} provided as a parameter.</p>
 */
public enum UnitVector {

	/**
	 * The singleton instance for the unit vector <i>i</i>.
	 */
	I(new Vector(1, 0)),

	/**
	 * The singleton instance for the unit vector <i>j</i>.
	 */
	J(new Vector(1, 1)),

	/**
	 * The singleton instance for the unit vector <i>k</i>.
	 */
	K(new Vector(0, 1)),

	/**
	 * The singleton instance for the unit vector <i>l</i>.
	 */
	L(new Vector(-1, 0)),

	/**
	 * The singleton instance for the unit vector <i>m</i>.
	 */
	M(new Vector(-1, -1)),

	/**
	 * The singleton instance for the unit vector <i>n</i>.
	 */
	N(new Vector(0, -1));

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
	 * Returns the six unit vectors as {@code Vector}, ordered in the trigonometric (anticlockwise) sense,
	 * for facilitating the tile position operations. Each of these six vectors could be used to point,
	 * in a unique way, to one of the six sides of a tile of which the coordinates define an application common point.
	 * 
	 * <p>Note that this method is often to be preferred to the {@code values()} (implicit) method.</p>
	 * 
	 * @return
	 *  the six unit vectors in the trigonometric sense.
	 */
	public static Vector[] getVectors() {

		Vector[] unitVectors = new Vector[6];

		for (int i = 0; i < 6; i++) {
			unitVectors[i] = (UnitVector.values()[i]).getVector();
		}

		return unitVectors;
	}

}
