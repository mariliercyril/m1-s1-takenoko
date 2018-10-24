package com.raccoon.takenoko.tool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.apache.log4j.Logger;

public final class PropertiesFileReader {

	private static final Logger LOGGER = Logger.getLogger(PropertiesFileReader.class);

	private static final String PROPERTIES_FILE_EXTENSION = "properties";

	private static final String KEY_NAME_FORMAT = "%s" + Separator.POINT.getSign() + "%s";

	private PropertiesFileReader() {
	}

    private static class PropertiesFileReaderHolder {

        private static final PropertiesFileReader INSTANCE = new PropertiesFileReader();
    }

    public static PropertiesFileReader getInstance() {

        return PropertiesFileReaderHolder.INSTANCE;
    }

	@Override
	public Object clone() throws CloneNotSupportedException {

		throw new CloneNotSupportedException();
	}

	public String getStringProperty(String propertiesFileName, String key, String defaultValue) {

		String keyName = String.format(KEY_NAME_FORMAT, propertiesFileName, key);

		return (this.readPropertiesFile(propertiesFileName)).getProperty(keyName, defaultValue);
	}

	public int getIntProperty(String propertiesFileName, String key, int defaultValue) {

		return Integer.parseInt(this.getStringProperty(propertiesFileName, key, String.valueOf(defaultValue)));
	}

	public float getFloatProperty(String propertiesFileName, String key, float defaultValue) {

		return Float.parseFloat(this.getStringProperty(propertiesFileName, key, String.valueOf(defaultValue)));
	}

	private Properties readPropertiesFile(String propertiesFileName) {

		Properties properties = new Properties();

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

		return properties;
	}

}
