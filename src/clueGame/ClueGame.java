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
		board.loadBoardConfig(configFile, rooms);
	}
	
	//Has to be public for testing.
	private void loadLegend() throws BadConfigFormatException{
		File file = new File(legendFile);
		try{
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			String line;
			while((line = bReader.readLine()) != null){
				//format is C, val
				rooms.put(line.charAt(0), line.substring(3, line.length()));
			}
			bReader.close();
		} catch (FileNotFoundException e){
			System.out.println("File " + legendFile + " not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
