package com.raccoon.takenoko.tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertiesFileReader {

	private static final Logger LOGGER = Logger.getLogger(PropertiesFileReader.class);

	private static final String PROPERTIES_FILE_EXTENSION = "properties";

	private static Properties properties = new Properties();

	private static PropertiesFileReader instance;

	private PropertiesFileReader() {
	}

	public static PropertiesFileReader getInstance() {

		if (null == instance) {
			instance = new PropertiesFileReader();
		}

		return instance;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		throw new CloneNotSupportedException();
	}

	public void getProperties(String propertiesFileName) {

		String propertiesFilePath = Separator.SLASH.getSign() + propertiesFileName + Separator.POINT.getSign() + PROPERTIES_FILE_EXTENSION;
		try {
			InputStream inputStream = getClass().getResourceAsStream(propertiesFilePath);
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException fnfe) {
			LOGGER.debug("Properties file", fnfe);
		} catch (IOException ioe) {
			LOGGER.debug("Input", ioe);
		}
	}

	public String getStringProperty(String key, String defaultValue) {

		return properties.getProperty(key, defaultValue);
	}

	public int getIntProperty(String key, int defaultValue) {

		return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
	}

	public float getFloatProperty(String key, float defaultValue) {

		return Float.parseFloat(properties.getProperty(key, String.valueOf(defaultValue)));
	}

}
