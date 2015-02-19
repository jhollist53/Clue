package clueGame;

public class RoomCell extends BoardCell {
	
	public enum DoorDirection{UP, DOWN, LEFT, RIGHT, NONE};
	public DoorDirection doorDirection;
	private char roomInitial;
	
	@Override
	public boolean isRoom () {
		return true;
	}
	
}