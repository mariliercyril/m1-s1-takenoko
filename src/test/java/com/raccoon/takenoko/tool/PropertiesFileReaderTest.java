package com.raccoon.takenoko.tool;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class allows to complete test of the class {@link PropertiesFileReader}.
 */
public class PropertiesFileReaderTest {

	private static PropertiesFileReader propertiesFileReader;

	@BeforeAll
	public static void createsMockPlayer() {

		propertiesFileReader = PropertiesFileReader.getInstance();
	}

	/*
	 * When the CheckIfCompleted method is used with a tile and a board (as paramters)
	 */
	@Test
	@DisplayName("assert that it throws CloneNotSupportedException when we try to clone the PropertiesFileReader")
	public void testClone() {

        assertThrows(CloneNotSupportedException.class, () -> propertiesFileReader.clone());
	}

}
