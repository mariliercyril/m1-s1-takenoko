package com.raccoon.takenoko.tool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import org.apache.log4j.spi.LoggingEvent;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This class allows to complete test of the class {@link PropertiesFileReader}.
 */
public class PropertiesFileReaderTest {

	private static PropertiesFileReader propertiesFileReader;

	@BeforeAll
	public static void createsMockPlayer() {

		propertiesFileReader = PropertiesFileReader.getInstance();
	}

	@Test
	@DisplayName("assert that it equals NullPointerException when we try to get properties from a file which does not exist"
			+ "(or a file of which the name is null)")
	public void testGetProperty() {

		// Gets the LOGGER of the PropertiesFileReader
		final Logger LOGGER = Logger.getLogger(PropertiesFileReader.class);
		// A mock appender
		Appender mockAppender = mock(Appender.class);

		// Adds the mock appender to the LOGGER
		LOGGER.addAppender(mockAppender);


		// Tries to get property with the name of a file which does not exist
		propertiesFileReader.getStringProperty("fileName", "key", "defaultValue");

		// Captures the return of the LOGGER, which has just been captured
		// when we try to get property with the name of a file which does not exist
		ArgumentCaptor<LoggingEvent> argument = ArgumentCaptor.forClass(LoggingEvent.class);
		verify(mockAppender).doAppend(argument.capture());

		// Asserts the same thing when we try to get property with a file name which is null
		assertEquals(NullPointerException.class.getName(), argument.getValue().getMessage().toString());


		// Removes the mock appender from the LOGGER
		LOGGER.removeAppender(mockAppender);
	}

}
