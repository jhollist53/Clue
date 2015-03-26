package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField name;

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
		JButton agree = new JButton("Next Player");
		JButton disagree = new JButton("Make Accusation");
		JPanel panel = new JPanel();
		panel.add(agree);
		panel.add(disagree);
		return panel;
	}
	private JPanel createOutPanels() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JPanel panel2 = new JPanel();
		
		JLabel nameLabel = new JLabel("Die Roll:");
		panel2.add(nameLabel);
		JTextField a = new JTextField(20);
		panel2.add(a);
		a.setEditable(false);
		panel.add(panel2);
		
		nameLabel = new JLabel("Guess: ");
		panel2.add(nameLabel);
		a = new JTextField(20);
		panel2.add(a);
		a.setEditable(false);
		panel.add(panel2);
		
		nameLabel = new JLabel("Result:");
		panel2.add(nameLabel);
		a = new JTextField(20);
		panel2.add(a);
		a.setEditable(false);
		panel.add(panel2);
		
		
		return panel;
	}
	
	/*public static void main(String[] args) {
		ControlGUI gui = new ControlGUI();	
		gui.setVisible(true);
	}*/


}