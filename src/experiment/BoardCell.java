package experiment;

public class BoardCell {
	private int row, column;
	
	public BoardCell(int column, int row){
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

}
