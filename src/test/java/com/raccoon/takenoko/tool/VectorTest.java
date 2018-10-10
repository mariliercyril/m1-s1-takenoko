package com.raccoon.takenoko.tool;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class allows to test methods of the class {@link Vector}.
 */
public class VectorTest {

	private static Vector vector;

	@BeforeAll
	public static void constructVector() {

		vector = new Vector(1, 1);
	}

	@Test
	@DisplayName("assert true when a Vector (X, Y) allows to have a translation from P (x, x) to P' (x + X, y + Y)")
	public void testApply() {

		assertTrue((vector.apply(new Point(1, 2))).equals(new Point(2, 3)));
	}

	@Test
	@DisplayName("assert true when the opposite of the Vector (X, Y) has the coordinates -X and -Y")
	public void testOpposite_true() {

		assertTrue((vector.getOpposite()).equals(new Point(-1, -1)));
	}

	@Test
	@DisplayName("assert false when opposite of the Vector is the Vector")
	public void testOpposite_false() {

		assertFalse((vector.getOpposite()).equals(new Point(1, 1)));
	}

	@Test
	@DisplayName("assert true when the sum of Vector (X1, Y1), (X2, Y2), ... , (Xn, Yn) equals the Vector (X1 + X2 + ... + Xn, Y1 + Y2 + ... + Yn)")
	public void testSum_true() {

		Vector v1 = (UnitVector.I).getVector();
		Vector v2 = (UnitVector.J).getVector();
		Vector vn = new Vector(new Point(1, 1));

		assertTrue((Vector.sum(v1, v2, vn)).equals(new Point(2, 2)));
	}

	@Test
	@DisplayName("assert true when the rotation of a Vector is done in the positive trigonometric (anticlockwise) sense")
	public void testRotation_truePositiveSense() {

		Vector v = new Vector(new Point(1, 1));

		assertTrue(v.rotation(4).equals(new Point(0, -1)));
	}

	@Test
	@DisplayName("assert true when the rotation of a Vector is done in the negative clockwise sense")
	public void testRotation_trueNegativeSense() {

		Vector v = new Vector(new Point(1, 1));

		assertTrue(v.rotation(-2).equals(new Point(0, -1)));
	}

}
