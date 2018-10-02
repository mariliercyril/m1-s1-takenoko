package com.raccoon.takenoko.tool;

/**
 * This interface provides the unit vectors.
 */
public enum UnitVector {

	// The Unit Vector I and the Unit Vector J
	I(new Vector(1, 0)),
	J(new Vector(0, 1)),

	// The Vector K (a third Unit Vector)
	K(Vector.sum((UnitVector.I).get(), (UnitVector.J).get()));

	private Vector vector;

	private UnitVector(Vector vector) {

		this.vector = vector;
	}

	public Vector get() {

		return vector;
	}

}
