package clueGame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class BoardGUI extends JFrame {
	private DrawPanel drawPanel;
	private int dx, dy;
	public BoardGUI() {
		drawPanel = new DrawPanel(2, 1);
		add(drawPanel, BorderLayout.CENTER);
		setSize(300, 300);  
	}
	
public void updateDrawing(int dx, int dy) {
	this.dx = dx;
	this.dy = dy;
	Timer t = new Timer(1000, new TimerListener());
	t.start(); 
	}

private class TimerListener implements ActionListener {
	public void actionPerformed1(ActionEvent e) {
		drawPanel.translate(dx, dy); }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	} }

}