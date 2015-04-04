package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField name;
	private JTextField roll;
	private JTextField guess;
	private JTextField result;

	public ControlGUI()
	{
		setSize(600, 200);
		setLayout(new GridLayout(3,3));
		JPanel panel = createWhoseTurnPanel();
		add(panel);
		panel = createOutPanels();
		add(panel);
		panel = createButtonPanel();
		add(panel);
	}

	 private JPanel createWhoseTurnPanel() {
		 	JPanel panel = new JPanel();
		 	JLabel nameLabel = new JLabel("Whose Turn");
		 	panel.setLayout(new GridLayout(2,1));
			name = new JTextField(20);
			panel.add(nameLabel);
			panel.add(name);
			panel.setBorder(new TitledBorder (new EtchedBorder()));
			return panel;
	}
	 
	private JPanel createButtonPanel() {
		JButton nextplayer = new JButton("Next Player");
		JButton accuse = new JButton("Make Accusation");
		JPanel panel = new JPanel();
		panel.add(nextplayer);
		panel.add(accuse);
		
		class NextButton implements ActionListener {
		    public void actionPerformed(ActionEvent e)
		    {
		    	ClueGame.nextPlayer();
		    }
		}
		nextplayer.addActionListener(new NextButton());
		  
		return panel;
	}
	private JPanel createOutPanels() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JPanel panel2 = new JPanel();
		
		JLabel nameLabel = new JLabel("Die Roll:");
		panel2.add(nameLabel);
		roll = new JTextField(20);
		panel2.add(roll);
		roll.setEditable(false);
		panel.add(panel2);
		
		nameLabel = new JLabel("Guess: ");
		panel2.add(nameLabel);
		guess = new JTextField(25);
		panel2.add(guess);
		guess.setEditable(false);
		panel.add(panel2);
		
		nameLabel = new JLabel("Response:");
		panel2.add(nameLabel);
		result = new JTextField(20);
		panel2.add(result);
		result.setEditable(false);
		panel.add(panel2);
		
		
		return panel;
	}
	
	public void setWho(String a){
		name.setText(a);
	}
	public void setRoll(int a){
		roll.setText(String.valueOf(a));
	}
	public void setGuess(Card person, Card weapon, Card Place){
		guess.setText(person.getName() + " " + weapon.getName() + " " + Place.getName());
	}
	public void setResult(Card a){
		result.setText(a.getName());
	}

	public void suggestout(ArrayList<Card> suggestion) {
		guess.setText(suggestion.get(0).getName() +", "+ suggestion.get(1).getName() +", "+ suggestion.get(2).getName());
		
	}
	public void clearGuess() {
		guess.setText("");
	}
	public void clearResult() {
		result.setText("");
	}
	
	
	/*public static void main(String[] args) {
		ControlGUI gui = new ControlGUI();	
		gui.setVisible(true);
	}*/


}