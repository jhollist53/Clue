package clueGame;

public abstract class BoardCell {
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
	
	public boolean isDoorway () {
		return true;
	}
}
