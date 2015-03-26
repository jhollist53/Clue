package clueGame;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class DrawPanel extends JPanel {
	private int x, y;
	public DrawPanel(int a, int b)
	{
		x = a;
		y = b;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		g.drawRect(x, y, 20, 20);
	}

	public void translate(int dx, int dy)
	{
		x += dx;
		y += dy;
		// Must include this to see changes
		repaint();
	}
}