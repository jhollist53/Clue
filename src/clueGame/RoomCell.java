package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
	public DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(int x, int y, char roomInitial, DoorDirection door){
		this.x = x;
		this.y = y;
		this.roomInitial = roomInitial;
		this.doorDirection = door;
	}
	
	@Override
	public boolean isRoom () {
		return true;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return roomInitial;
	}
	
	@Override
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE){ return false; }
		else { return true; }
	}

}
