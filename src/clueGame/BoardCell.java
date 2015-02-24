package clueGame;

public abstract class BoardCell implements Comparable<BoardCell>{
	protected int x;
	protected int y;

	BoardCell() {
		isWalkway();
		isRoom();
		isDoorway();
	}
		
	public boolean isWalkway () {
		return false;
	}
	
	public boolean isRoom () {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	public int compareTo(BoardCell other) {
		int dx = this.x - other.x;
		//Nested tertiary operators ftw
		return dx == 0 ? this.y - other.y : (dx + this.y - other.y) == 0 ? -1 : (dx + this.y - other.y);
	}
}
