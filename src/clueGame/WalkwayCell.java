package clueGame;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell() {
		this.x = 0;
		this.y = 0;
	}
	
	public WalkwayCell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isWalkway () {
		return true;
	}
}
