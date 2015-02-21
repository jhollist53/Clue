package clueGame;

import java.io.*;
import java.util.*;

public class ClueGame {
	private Map<Character, String> rooms;
	private String configFile, legendFile;
	private Board board;
	
	public ClueGame(String configFile, String legendFile) {
		this.configFile = configFile;
		this.legendFile = legendFile;
		board = new Board();
		rooms = new HashMap<Character, String>();
	}

	public Board getBoard () {
		return board;
	}
	
	public void loadConfigFiles () {
		try{
			loadLegend();
		} catch (BadConfigFormatException e){
			System.out.println(e);
		}
		board.loadBoardConfig(configFile);
	}
	
	//Helper functions, all private.
	private void loadLegend() throws BadConfigFormatException{
		Scanner scanner = new Scanner(legendFile);
		while(scanner.hasNextLine()){
			String temp = scanner.nextLine();

			//format is C, val
			rooms.put(temp.charAt(0), temp.substring(3, temp.length()));
		}
		scanner.close();
	}
}
