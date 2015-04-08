package core;

public class Wall extends DungeonSquare {
	
	protected int m_sideOfRoom;
	protected boolean m_isNextToRoom;

	public Wall(int x, int y, int sideOfRoom, boolean isNextToRoom) {
		super(x, y);
		this.m_sideOfRoom = sideOfRoom;
		this.m_isNextToRoom = isNextToRoom;
	}

	public int getSide() {
		return this.m_sideOfRoom;
	}
	
	public String toString() {
		return this.m_coords.getElement0().toString() + ", " + this.m_coords.getElement1().toString() + " " + this.m_sideOfRoom + " side";
	}
	
	public boolean isNextToRoom() {
		return this.m_isNextToRoom;
	}


}
