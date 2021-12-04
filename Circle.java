package JavaGraphics;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape{

	public Circle(int x, int y, int r, Color c) {
		super(x, y, r, r, c);
	}

	public Shape copy() {
		return new Circle(x, y, width, c);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, width, width);
	}

	public boolean isOn(int a, int b) {
		if(distance(a, b, centerX(x, x+width), centerX(y ,y+width)) < width) {
			return true;
		}
		return false;
	}
	
	public int centerX(int x, int a) {
		return (int)(x+a)/2;
	}
	
	public int distance(int x, int y, int a, int b) {
		return (int)(Math.sqrt(Math.pow(x-a, 2) + Math.pow(y-b, 2)));
	}

	public void resize(int x1, int y1, int x2, int y2) {
		width = Math.abs(x1-x2);
		x = Math.min(x1, x2);
		y = Math.min(y1, y2);
		
	}

	public void addPoint(Point x) {
		// TODO Auto-generated method stub
		
	}

}