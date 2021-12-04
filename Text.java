package JavaGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextArea;

public class Text extends Shape{
	String str = ""; 
	int fontSize = 10;
	
	public Text(int x, int y, String s, Color c, int fs) {
		super(x, y, 0, 0, c);
		str = s;
		fontSize = fs;
	}

	public Shape copy() {
		return new Text(x, y, str, c, fontSize);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		g.drawString(str, x, y);
	}

	public boolean isOn(int a, int b) {
		// what would be the size of a piece of text
		return false;
	}

	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addPoint(Point x) {}

}
