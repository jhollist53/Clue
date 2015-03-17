package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	
	private void deal() {}
	
	private void selectAnswer() {}
	
	private void handleSuggestion( String person, String room, String weapon, Player accusingPlayer ) {}
	
	private void checkAccusation(Solution solution) {}
	
	
}
