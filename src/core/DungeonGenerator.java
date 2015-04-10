package core;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


/*
    1. Fill the whole map with solid earth
    2. Dig out a single room in the center of the map
    3. Pick a DungeonSquare of any room
    4. Decide upon a new feature to build
    5. See if there is room to add the new feature through the chosen DungeonSquare
    6. If yes, continue. If no, go back to step 3
    7. Add the feature through the chosen DungeonSquare
    8. Go back to step 3, until the dungeon is complete
    9. Add the up and down staircases at random points in map
    10. Finally, sprinkle some monsters and items liberally over dungeon
*/

/**
 * This class is for generation of random dungeons. It takes an integer as its only parameter and
 * creates a square grid from that. When the user calls GenerateDungeon, the blank dungeon is filled
 * with features that are randomly generated 
 * @author Josh
 *
 */
public class DungeonGenerator {

	private DungeonConstants.Tile[][] m_grid;
	private ArrayList<AbsoluteWall> m_potentialWalls;
	private int m_size;
	private RNG m_rng;
	private int MAX_ROOM_SIZE;
	private int m_solidEarthRemaining;
	private int m_stoppingCondition;

	/**
	 * Constructor. Used to initialize the object for a call to generateDungeon
	 * @param size used to determine the size of the square grid to be created.
	 * @param stoppingCondition determines when the generation stops, based on % of
	 * 			of the dungeon being filled (e.g. .75 means the generator will stop at
	 * 			75% capacity of feature filling.
	 */
	public DungeonGenerator(int size, double stoppingCondition) 	{

		// if it's outside the size bounds, then abandon ship
		if (size < DungeonConstants.MIN_DUNGEON_SIZE) {
			return;
		}
		if (size > DungeonConstants.MAX_DUNGEON_SIZE) {
			return;
		}

		// if it's not even, then make it even.
		if (size % 2 == 1) {
			size += 1;
		}
		this.m_size = size;
		this.m_grid = new DungeonConstants.Tile[size][size];

		// 1. Fill the whole map with solid earth
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				this.m_grid[i][j] = DungeonConstants.Tile.Unpassable;
			}
		}

		this.m_rng = new RNG();
		
		// TODO: make configurable?
		this.MAX_ROOM_SIZE = size / 4;
		
		this.m_potentialWalls = new ArrayList<AbsoluteWall>();
		this.m_solidEarthRemaining = size * size;
		this.m_stoppingCondition = (int) (m_solidEarthRemaining * stoppingCondition);
	}

	/**
	 * Generates a dungeon in a blank grid. Behavior is undefined for a 
	 * second call. Each dungeon generated should create a new DungeonGenerator Object.
	 */
	public void GenerateDungeon() {
		// 2. Dig out a single room in the center of the map
		DungeonSquare center = new DungeonSquare (m_size / 2, m_size / 2);
		
		int firstRoomLength = m_rng.GetRandomInteger(DungeonConstants.MIN_ROOM_SIZE, MAX_ROOM_SIZE);
		int firstRoomWidth = m_rng.GetRandomInteger(DungeonConstants.MIN_ROOM_SIZE, MAX_ROOM_SIZE);
		Room firstRoom = new Room(firstRoomLength, firstRoomWidth);
		placeFirstRoom(firstRoom, center);
		
		//3. choose a wall from which to build
		//break out of this when dungeon is complete.
		do {
			int startingWallInd = m_rng.GetRandomInteger(0, m_potentialWalls.size() - 1);
			AbsoluteWall startingWall = m_potentialWalls.get(startingWallInd);
	
			for (int i = 0; i < DungeonConstants.NUMBER_OF_TRIES; i++) {
				Room newRoom;
				if (startingWall.isNextToRoom()) {
					
					//4. decide upon a new feature to build.
					int newCorridorLength = m_rng.GetRandomInteger(DungeonConstants.MIN_ROOM_SIZE, MAX_ROOM_SIZE);
					int side = startingWall.getSide();
					int horizontalOrVertial = (side == DungeonConstants.TOP || side == DungeonConstants.BOTTOM) ? 1 : 0;
					// horizontal
					if (horizontalOrVertial == 0) {
						newRoom = new Room(1, newCorridorLength);
					}
					else {
						newRoom = new Room(newCorridorLength, 1);
					}
				}
				else {
					int newRoomSize = m_rng.GetRandomInteger(DungeonConstants.MIN_ROOM_SIZE, MAX_ROOM_SIZE);
					newRoom = new Room(newRoomSize, newRoomSize);
				}
							
				
				// find a wall on the prospective feature that matches the
				// orientation of the selected wall from which we are building.
				RelativeWall w;
				do {
					int newWallInd = m_rng.GetRandomInteger(0, newRoom.getRelativeWalls().size() - 1);
					w = newRoom.getRelativeWalls().get(newWallInd);
					// if they don't match up, remove this from future iterations and skip it.
					if (matchWalls(startingWall, w)) {
						break;
					}
					newRoom.getRelativeWalls().remove(newWallInd);
				} while (w != null);
				
				// this newRoom is unusable, so try again.
				if (w == null) {
					continue;
				}
				
				int x, y;
				// this wall is now usable. Find out where the top left corner is.
				DungeonSquare buildStart;
				switch (startingWall.getSide()) {
					case DungeonConstants.LEFT:
						x = startingWall.getX() - newRoom.getWidth() + w.getX() + 1;
						y = startingWall.getY() - newRoom.getLength() + 1;
						break;
					case DungeonConstants.TOP:
						x = startingWall.getX() - newRoom.getWidth() + 1;
						y = startingWall.getY() - w.getY();
						break;
					case DungeonConstants.RIGHT:
						x = startingWall.getX() - newRoom.getWidth() + w.getX() + 1;
						y = startingWall.getY();
						break;
					case DungeonConstants.BOTTOM:
						x = startingWall.getX();
						y = startingWall.getY() - newRoom.getLength() + w.getY() + 1;
						break;
					// shouldn't ever reach this.
					default:
						x = 0;
						y = 0;
						break;
					}
				buildStart = new DungeonSquare(x, y);
				
				// give the room its dungeon squares based on the build start dungeonsquare
				buildRoom(newRoom, buildStart, startingWall);
				
				//5. See if there is room to add the new feature through the chosen wall.
				
				// determine if this newly constructed room is valid
				boolean validRoom = validateRoom(newRoom, startingWall, w);
				
				//6. If yes, continue. If no, go back to step 3.
				if (validRoom) {
					// 7. add the feature through the chosen wall.
					placeRoom(newRoom, buildStart);
					
					// remove the previously used wall from the potential candidates.
					m_potentialWalls.remove(startingWall);
					
					// this code does a few things:
					// first, it removes walls where two features meet, to keep high definition 
					//		of features.
					// second, it adds walls that are unique and new from the new feature into the 
					// 		list of possible candidates going forward.
					ArrayList<AbsoluteWall> absw = newRoom.getAbsoluteWalls();
					for (AbsoluteWall a: absw) {
						if (m_potentialWalls.contains(a)) {
							while (m_potentialWalls.remove(a));
							
						}
						else{
							m_potentialWalls.add(a);
						}
					}
					break;
				}
				
			}
		} while (this.m_solidEarthRemaining > m_stoppingCondition);
		
		placeStairs();
	}

	/**
	 * randomly chooses a wall from the available candidates for use in the generation algorithm.
	 * @return the wall that was randomly chosen.
	 */
	public Wall chooseWall() {
		if (m_potentialWalls.size() == 0) {
			return null;
		}
		int wallToChooseInd = this.m_rng.GetRandomInteger(0, m_potentialWalls.size() - 1);
		Wall chosenWall = m_potentialWalls.get(wallToChooseInd);
		return chosenWall;
	}

	/**
	 * places the first room and adds its walls to the candidate list
	 * @param firstRoom Room to be placed.
	 * @param center the center of the grid
	 */
	public void placeFirstRoom(Room firstRoom, DungeonSquare center) {
		int x = center.getX();
		int y = center.getY();
		for (int i = 0; i < firstRoom.getLength(); i++ ) {
			for (int j = 0; j < firstRoom.getWidth(); j++ ) {
				m_grid[x + i][y + j] = DungeonConstants.Tile.Passable;
			}
		}
		for (int i = 0; i < firstRoom.getWidth(); i++) {
			m_potentialWalls.add(new AbsoluteWall(x - 1, y + i, DungeonConstants.TOP, true));
			m_potentialWalls.add(new AbsoluteWall(x + firstRoom.getLength(), y + i, DungeonConstants.BOTTOM, true));
		}
		for (int i = 0; i < firstRoom.getLength(); i++) {
			m_potentialWalls.add(new AbsoluteWall(x + i, y - 1, DungeonConstants.LEFT, true));
			m_potentialWalls.add(new AbsoluteWall(x + i, y + firstRoom.getWidth(), DungeonConstants.RIGHT, true));
		}
	}
	
	/**
	 * determines if the walls are properly matched up for better looking dungeons
	 * @param first first wall being considered
	 * @param second the second wall being considered
	 * @return boolean indicating whether these two walls are compatible.
	 */
	public boolean matchWalls(Wall first, Wall second) {
		switch (first.getSide()) {
			case DungeonConstants.LEFT:
				if (second.getSide() == DungeonConstants.RIGHT) {
					return true;
				}
				break;
			case DungeonConstants.TOP:
				if (second.getSide() == DungeonConstants.BOTTOM) {
					return true;
				}
				break;
			case DungeonConstants.RIGHT:
				if (second.getSide() == DungeonConstants.LEFT) {
					return true;
				}
				break;
			case DungeonConstants.BOTTOM:
				if (second.getSide() == DungeonConstants.TOP) {
					return true;
				}
				break;
			default:
				return false;
		}
		return false;
	}
	
	/**
	 * Determines whether the room chosen is a valid placement for this room.
	 * @param toValidate the room to be validated
	 * @param startingWall the existing, mapped wall from which we are building
	 * @param w The hypothetical, nonexistent wall on the candidate room.
	 * @return a boolean indicating whether this room is fit for placement.
	 */
	public boolean validateRoom(Room toValidate, AbsoluteWall startingWall, RelativeWall w) {
		for (DungeonSquare d: toValidate.getDungeonSquares()) {
			if (d.getX() >= m_grid.length || d.getX() < 0) {
				return false;
			}
			if (d.getY() >= m_grid[0].length || d.getY() < 0) {
				return false;
			}
			if (m_grid[d.getX()][d.getY()] != DungeonConstants.Tile.Unpassable) {
				return false;
			}
		}
		
		// walls that are near the build start point are okay to have. Otherwise nothing would build.
		ArrayList<AbsoluteWall> preApprovedWalls = new ArrayList<AbsoluteWall>();
		switch (startingWall.getSide()) {
			case DungeonConstants.LEFT:
				for (int i = 0; i < toValidate.getWidth(); i++) {
					preApprovedWalls.add(new AbsoluteWall(startingWall.getX() - w.getX() + i, startingWall.getY() + 1, DungeonConstants.RIGHT, false));
				}
				break;
			case DungeonConstants.TOP:
				for (int i = 0; i < toValidate.getLength(); i++) {
					preApprovedWalls.add(new AbsoluteWall(startingWall.getX() + 1, startingWall.getY() - w.getY() + i, DungeonConstants.RIGHT, false));
				}
				break;
			case DungeonConstants.RIGHT:
				for (int i = 0; i < toValidate.getWidth(); i++) {
					preApprovedWalls.add(new AbsoluteWall(startingWall.getX() - w.getX() + i, startingWall.getY() - 1, DungeonConstants.RIGHT, false));
				}
				break;
			case DungeonConstants.BOTTOM:
				for (int i = 0; i < toValidate.getLength(); i++) {
					preApprovedWalls.add(new AbsoluteWall(startingWall.getX() - 1, startingWall.getY() - w.getY() + i, DungeonConstants.RIGHT, false));
				}
				break;
		default:
			break;
		}
		
		// if the walls overlap with other features, then reject this room.
		for (AbsoluteWall a: toValidate.getAbsoluteWalls()) {
			int x = a.getX();
			int y = a.getY();
			if (x < 0 || x >= m_grid.length || y < 0 || y >= m_grid[0].length) {
				return false;
			}
			if (m_grid[a.getX()][a.getY()] != DungeonConstants.Tile.Unpassable && !preApprovedWalls.contains(a)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Extrapolates which tiles this room will occupy, including walls.
	 * @param newRoom the room that is to be made concrete.
	 * @param buildStart the top left corner of the potential room
	 * @param anchor the existing, mapped location onto which this room will be built.
	 */
	public void buildRoom(Room newRoom, DungeonSquare buildStart, AbsoluteWall anchor) { 
		ArrayList<DungeonSquare> occupiedTiles = new ArrayList<DungeonSquare>();
		ArrayList<AbsoluteWall> absWalls = new ArrayList<AbsoluteWall>();
		
		// if one of the metrics is == 1, then this is a corridor.
		boolean isNextToRoom = !newRoom.isCorridor();
		
		// add all the squares within the room to the dungeonsquares list.
		for (int i = 0; i < newRoom.getWidth(); i++) {
			for (int j = 0; j < newRoom.getLength(); j++) {
				occupiedTiles.add(new DungeonSquare(buildStart.getX() + i, buildStart.getY() + j));
			}
		}
		
		int x = buildStart.getX();
		int y = buildStart.getY();
		
		// we need all of the wall information now, so generate it.
		//L
		for (int i = 0; i < newRoom.getWidth(); i++) {
			absWalls.add(new AbsoluteWall(x + i, y - 1, DungeonConstants.LEFT, isNextToRoom ));
		}
		
		//R
		for (int i = 0; i < newRoom.getWidth(); i++) {
			absWalls.add(new AbsoluteWall(x + i, y + newRoom.getLength(), DungeonConstants.RIGHT, isNextToRoom ));
		}
		
		//T
		for (int i = 0; i < newRoom.getLength(); i++) {
			absWalls.add(new AbsoluteWall(x - 1, y + i, DungeonConstants.TOP, isNextToRoom ));
		}
		
		//B
		for (int i = 0; i < newRoom.getLength(); i++) {
			absWalls.add(new AbsoluteWall(x + newRoom.getWidth(), y + i, DungeonConstants.BOTTOM, isNextToRoom ));
		}
		
		Wall[] w = new Wall[4];
		// top left corner
		w[0] = new Wall(x - 1, y - 1, 4, false);
		// top right corner
		w[1] = new Wall(x - 1, y + newRoom.getLength(), 4, false);
		// bottom left corner
		w[2] = new Wall(x + newRoom.getWidth(), y - 1, 4, false);
		// bottom right corner
		w[3] = new Wall(x + newRoom.getWidth(), y + newRoom.getLength(), 4, false);
		
		newRoom.setCorners(w);
		newRoom.setAbsoluteWalls(absWalls);
		newRoom.setDungeonSquares(occupiedTiles);
	}
	
	
	/**
	 * places the room into the grid.
	 * @param toPlace the room to be placed
	 * @param buildStart the location from which to build (top left corner)
	 */
	public void placeRoom(Room toPlace, DungeonSquare buildStart) {
		if (toPlace.getDungeonSquares().size() == 0) {
			return;
		}
		for (DungeonSquare d: toPlace.getDungeonSquares()) {
			m_grid[d.getX()][d.getY()] = DungeonConstants.Tile.Passable;
			this.m_solidEarthRemaining--;
			//System.out.println(this.m_solidEarthRemaining);
			//System.out.println(this.m_stoppingCondition);
		}
	}
	
	public void placeStairs() {
		
		boolean downstairsPlaced = false;
		boolean upstairsPlaced = false;
		int x, y;
		while (!downstairsPlaced) {
			x = m_rng.GetRandomInteger(0, m_size - 1);
			y = m_rng.GetRandomInteger(0, m_size - 1);
			if (m_grid[x][y] == DungeonConstants.Tile.Passable) {
				m_grid[x][y] = DungeonConstants.Tile.Downstairs;
				downstairsPlaced = true;
			}
		}
		while (!upstairsPlaced) {
			x = m_rng.GetRandomInteger(0, m_size - 1);
			y = m_rng.GetRandomInteger(0, m_size - 1);
			if (m_grid[x][y] == DungeonConstants.Tile.Passable) {
				m_grid[x][y] = DungeonConstants.Tile.Upstairs;
				upstairsPlaced = true;
			}
		}
		
		
		
	}
	

	/**
	 * Method used to write the dungeon to a file for post-processing. Can
	 * also be used to write to console for debugging.
	 * @param writeToFile indicator specifying target of write.
	 */
	public void printDungeon(boolean writeToFile) {

		String toPrint = Integer.toString(this.m_size) + "\n";
		for (int i = 0; i < this.m_size; i++) {
			for (int j = 0; j < this.m_size; j++) {
				//Unpassable, Difficult, Passable, DungeonSquare, Chest, Monster, Obstacle
				switch(m_grid[i][j]) {
					case Unpassable:
						toPrint += 'X';
						break;
					case Passable:
						toPrint += '_';
						break;
					case Difficult:
						toPrint += 'D';
						break;
					case Wall:
						toPrint += 'W';
						break;
					case Chest:
						toPrint += 'C';
						break;
					case Monster:
						toPrint += 'M';
						break;
					case Obstacle:
						toPrint += 'O';
						break;
					case Upstairs:
						toPrint += 'U';
						break;
					case Downstairs:
						toPrint += 'S';
						break;
					default:
						toPrint += '#';
						break;
				}

				toPrint += ", ";

			}
			toPrint += '\n';
		}
		PrintWriter writer;
		if (writeToFile) {
			try {
				writer = new PrintWriter(DungeonConstants.SAVEDDUNGEONSDIR + "\\generatedDungeon.bnb", "UTF-8");
				writer.println(toPrint);
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		else {
			System.out.println(toPrint);
		}

	}
}