package com.raccoon.takenoko.tool;

public final class Constants {

	private Constants() {}  // Prevent this class to be constructed

	private static final String FILE_NAME = "takeyesntko";

	static {
		PropertiesFileReader.getInstance().getProperties(FILE_NAME);
	}

	public static final int MAX_AMOUNT_OF_OBJECTIVES = PropertiesFileReader.getInstance()
			.getIntProperty(FILE_NAME + Separator.POINT.getSign() + "max.amount.of.objectives", 2);
	public static final int NUMBER_OF_TILES_TO_DRAW = PropertiesFileReader.getInstance()
			.getIntProperty(FILE_NAME + Separator.POINT.getSign() + "number.of.tiles.to.draw", 3);

	public static final int MAX_BAMBOO_SIZE = PropertiesFileReader.getInstance()
			.getIntProperty(FILE_NAME + Separator.POINT.getSign() + "max.bamboo.size", 4);
	public static final int USUAL_BAMBOO_GROWTH = PropertiesFileReader.getInstance()
			.getIntProperty(FILE_NAME + Separator.POINT.getSign() + "usual.bamboo.growth", 1);

	public static final float NUMBER_OF_GAMES_FOR_STATS = PropertiesFileReader.getInstance()
			.getFloatProperty(FILE_NAME + Separator.POINT.getSign() + "number.of.games.for.stats", 10);
}
