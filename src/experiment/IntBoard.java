package experiment;
import java.util.*;


public class IntBoard {
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private ArrayList<BoardCell> board;
	private int xDim, yDim;
	
	public IntBoard(int x, int y){
		xDim = x;
		yDim = y;
		board = new ArrayList<BoardCell>();
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		for(int j = 0; j < y; j++){
			for(int i=0; i < x; i++){
				board.add(new BoardCell(i, j));
			}
		}
		for(int j = 0; j < y; j++){
			for(int i = 0; i < x; i++){
				adjMtx.put(board.get(i+j*xDim), this.calcAdjacencies(i, j));
			}
		}
	}
	
	private LinkedList<BoardCell> calcAdjacencies(int x, int y){
		LinkedList<BoardCell> ll = new LinkedList<BoardCell>();
		if(y-1 >= 0){
			ll.addLast(board.get((x) + (y-1)*xDim));
		}
		if(x+1 < xDim){
			ll.addLast(board.get((x+1) + y*xDim));
		}
		if(y+1 < yDim){
			ll.addLast(board.get(x + (y+1)*xDim));
		}
		if(x-1 >= 0){
			ll.addLast(board.get((x-1) + y*xDim));
		}
		return ll;
	}
	
	public void calcTargets(BoardCell cell, int num){
		cell = this.getCell(cell.getColumn(), cell.getRow());
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(cell);
		findAllTargets(cell, num);
	}
	
	private void findAllTargets(BoardCell startCell, int length){
		visited.add(startCell);
		for(BoardCell cell : adjMtx.get(startCell)){
			if(length == 1){
				if(!visited.contains(cell)){
					targets.add(cell);
					}
			}
			else {
				findAllTargets(cell, length - 1);
			}
			visited.remove(cell);
		}
	}
	
	public Set<BoardCell> getTargets(){
		
		return targets;
	}
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		cell = this.getCell(cell.getColumn(), cell.getRow());
		return adjMtx.get(cell);
	}
	public BoardCell getCell(int x, int y){
		
		return board.get(x+y*xDim);
	}
	public int getNumericCell(int x, int y){
		return x+y*xDim;
	}
}
