package com.raccoon.takenoko.tool;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
