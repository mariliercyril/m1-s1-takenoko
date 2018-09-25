package com.raccoon.takenoko.tool;

/**
 * This enumeration contains the unit vectors <i>I</i> and <i>J</i>.
 */
public enum UnitVector {

	I(0, 1),
	J(1, 0);

	public int x;
	public int y;

	private UnitVector(int x, int y) {

		this.x = x;
		this.y = y;
	}

	public Vector get() {

		return new Vector(x, y);
	}
}
