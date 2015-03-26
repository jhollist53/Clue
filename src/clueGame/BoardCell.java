package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class BoardCell extends JPanel implements Comparable<BoardCell>{
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
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(isWalkway()? Color.PINK: Color.ORANGE);
		g.fillRect(x*Board.sqsize, y*Board.sqsize, Board.sqsize-1, Board.sqsize-1);
		g.setColor(Color.BLUE);
		if(isDoorway()){
			if ( ((RoomCell) this).doorDirection == RoomCell.DoorDirection.RIGHT){
				g.fillRect((x+1)*Board.sqsize-2, y*Board.sqsize, 2, Board.sqsize-1);
			}
			if ( ((RoomCell) this).doorDirection == RoomCell.DoorDirection.LEFT){
				g.fillRect((x)*Board.sqsize-1, y*Board.sqsize, 2, Board.sqsize-1);
			}
			if ( ((RoomCell) this).doorDirection == RoomCell.DoorDirection.UP){
				g.fillRect((x)*Board.sqsize, y*Board.sqsize-1, Board.sqsize-1, 2);
			}
			if ( ((RoomCell) this).doorDirection == RoomCell.DoorDirection.DOWN){
				g.fillRect((x)*Board.sqsize, (y+1)*Board.sqsize-2, Board.sqsize-1, 2);
			}
		}
	}
}