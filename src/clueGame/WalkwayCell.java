package clueGame;

public class WalkwayCell extends BoardCell {
	
	public WalkwayCell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public boolean isWalkway () {
		return true;
	}
}
