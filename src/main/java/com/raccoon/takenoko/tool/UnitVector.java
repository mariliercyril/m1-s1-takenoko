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
	K(Vector.sum((UnitVector.I).get(), (UnitVector.J).get()));

	private Vector vector;

	private UnitVector(Vector vector) {

		this.vector = vector;
	}

	/**
	 * Returns the vector as a {@code Vector} object.
	 * 
	 * @return the vector as a <i>Vector</i> object.
	 */
	public Vector get() {

		return vector;
	}

}
