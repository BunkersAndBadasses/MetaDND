package core;

import java.io.File;

public final class DungeonConstants {
	public static final int MIN_DUNGEON_SIZE = 10;
	public static final int MAX_DUNGEON_SIZE = 100;
	public static final int MIN_ROOM_SIZE = 2;
	public static final int MIN_CORRIDOR_LENGTH = 2;
	public static final int NUMBER_OF_TRIES = 5;
	public static final int LEFT = 0;
	public static final int TOP = 1;
	public static final int RIGHT = 2;
	public static final int BOTTOM = 3;
	
	public static final File SAVEDDUNGEONSDIR = new File(GameState.USERDATAFOLDER, "Generated Dungeons");

	enum Tile {
	    Unpassable, Difficult, Passable, Wall, Chest, Monster, Obstacle, Upstairs, Downstairs
	}

}
