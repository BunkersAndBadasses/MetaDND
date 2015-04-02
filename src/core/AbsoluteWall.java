package core;



public class AbsoluteWall extends Wall{
	
	public AbsoluteWall(Pair<Integer, Integer> coords, int sideOfRoom, boolean isNextToRoom){
		super(coords.getElement0(), coords.getElement1(), sideOfRoom, isNextToRoom);
	}

	public AbsoluteWall(int x, int y, int sideOfRoom, boolean isNextToRoom) {
		super(x, y, sideOfRoom, isNextToRoom);
	}
	
	public boolean equals(Object otherWall) {
		if (otherWall instanceof AbsoluteWall) {
			AbsoluteWall absw = (AbsoluteWall) otherWall;
			return this.m_coords.getElement0() == absw.getCoords().getElement0() &&
					this.m_coords.getElement1() == absw.getCoords().getElement1();
		}
		return false;
	}
	
	public Pair<Integer, Integer> getCoords() {
		return this.m_coords;
	}

	
}
