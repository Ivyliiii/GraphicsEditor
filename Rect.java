package JavaGraphics;

import java.awt.Color;
import java.awt.Graphics;

// this is rectangle shape class
public class Rect extends Shape{
	
	public Rect(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}
	
	// for redo
	public Shape copy() {
		// basically creates a new rect that is exactly the same
		return new Rect(x, y, width, height, c);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillRect(x, y, width, height);
	}

	public boolean isOn(int a, int b) {
		if(a > x && a < x + width && b > y && b < y + height) {
			return true;
		}
		return false;
	}

	public void resize(int x1, int y1, int x2, int y2) {
		width = Math.abs(x2-x1);
		height = Math.abs(y2-y1);
		x = Math.min(x1, x2);
		y = Math.min(y1, y2);
	}

	@Override
	public void addPoint(Point x) {
		// TODO Auto-generated method stub
		
	}

}
