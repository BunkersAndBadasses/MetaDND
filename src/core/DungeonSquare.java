package core;



public class DungeonSquare {
		protected Pair<Integer, Integer> m_coords;

		public DungeonSquare(Pair<Integer, Integer> coords){
			this.m_coords = coords;
		}

		public DungeonSquare(int x, int y) {
			this.m_coords = new Pair<Integer, Integer>(x, y);
		}

		public boolean equals(Object otherSquare) {
			if (otherSquare instanceof DungeonSquare) {
				DungeonSquare ds = (DungeonSquare) otherSquare;
				return this.m_coords.getElement0() == ds.getCoords().getElement0() &&
						this.m_coords.getElement1() == ds.getCoords().getElement1();
			}
			return false;
		}
		
		public int getX() {
			return this.m_coords.getElement0();
		}
		
		public int getY() {
			return this.m_coords.getElement1();
		}

		public Pair<Integer, Integer> getCoords() {
			return this.m_coords;
		}
		
		public String toString() {
			return "(" + Integer.toString(this.m_coords.getElement0()) + ", " + Integer.toString(this.m_coords.getElement1()) + ")";
		}


}
