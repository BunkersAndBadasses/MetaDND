package core;
import java.util.ArrayList;

public class Room {

	private int length, width;
	private ArrayList<RelativeWall> m_relativeWalls;
	private ArrayList<AbsoluteWall> m_absoluteWalls;
	private Wall[] m_corners;
	private ArrayList<DungeonSquare> m_squaresToOccupy;

	public Room(int length, int width) {
		this.width = length;
		this.length = width;
		m_squaresToOccupy = new ArrayList<DungeonSquare>();
		m_relativeWalls = new ArrayList<RelativeWall>();
		m_absoluteWalls = new ArrayList<AbsoluteWall>();
		m_corners = new Wall[4];
		
		boolean isNextToRoom = (length != 1 && width != 1) ? true : false;
		
	    //L
		for (int i = 0; i < this.width; i++) {
			RelativeWall newWall = new RelativeWall(i, -1, DungeonConstants.LEFT, isNextToRoom);
			//System.out.println(newDungeonSquare);
			this.m_relativeWalls.add(newWall);
		}

		//R
		for (int i = 0; i < this.width; i++) {
			RelativeWall newWall = new RelativeWall(i, width, DungeonConstants.RIGHT, isNextToRoom);
			//System.out.println(newDungeonSquare);
			this.m_relativeWalls.add(newWall);
		}
		


		//T
		for (int i = 0; i < this.length; i++) {
			RelativeWall newWall = new RelativeWall(-1, i, DungeonConstants.TOP, isNextToRoom);
			//System.out.println(newDungeonSquare);
			this.m_relativeWalls.add(newWall);
		}
		
		//B
		for (int i = 0; i < this.length; i++) {
			RelativeWall newWall = new RelativeWall(length, i, DungeonConstants.BOTTOM, isNextToRoom);
			//System.out.println(newDungeonSquare);
			this.m_relativeWalls.add(newWall);
		}
		
		
	}
	
	//TODO
	public boolean equals(Room otherRoom) {
		return this.width == otherRoom.getWidth() && this.length == otherRoom.getLength();
	}

	public int getLength() {
		return this.length;
	}
	public int getWidth() {
		return this.width;
	}
	
	public ArrayList<RelativeWall> getRelativeWalls() {
		return this.m_relativeWalls;
	}
	
	public ArrayList<AbsoluteWall> getAbsoluteWalls() {
		return this.m_absoluteWalls;
	}
	
	public void setRelativeWalls(ArrayList<RelativeWall> toSet) {
		this.m_relativeWalls = toSet;
	}
	
	public void setAbsoluteWalls(ArrayList<AbsoluteWall> toSet) {
		this.m_absoluteWalls = toSet;
	}
	
	public void setCorners(Wall[] w) {
		this.m_corners = w;
	}
	
	public Wall[] getCorners() {
		return this.m_corners;
	}

	//TODO
	public String toString() {
		return "";
	}
	
	public boolean isCorridor() {
		return this.length == 1 || this.width == 1;
	}
	
	public void setDungeonSquares(ArrayList<DungeonSquare> toSet) {
		this.m_squaresToOccupy = toSet;
	}

	public ArrayList<DungeonSquare> getDungeonSquares() {
		return this.m_squaresToOccupy;
	}
	
}
