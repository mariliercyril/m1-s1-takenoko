package com.raccoon.takenoko.tool;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VectorTest {

	private Vector vector;

	@BeforeEach
	public void constructVector() {

		vector = new Vector(1, 1);
	}

	@Test
	public void testApply() {

		assertTrue((vector.apply(new Point(1, 2))).equals(new Point(2, 3)));
	}

	@Test
	public void testOpposite() {

		assertTrue((vector.opposite()).equals(new Point(-1, -1)));
	}

}
