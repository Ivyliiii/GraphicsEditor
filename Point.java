package JavaGraphics;

import java.awt.Color;
import java.awt.Graphics;

// this will makeup for the pen function
public class Point extends Shape{

	public Point(int x, int y, Color color) {
		super(x, y, 1, 1, color);
	}

	@Override
	public Shape copy() {
		return new Point(x, y, c);
	}

	@Override
	public void draw(Graphics g) {
		System.out.println("hi");
		g.setColor(c);
		g.fillOval(x, y, 50, 50);
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
	}

	@Override
	public void addPoint(Point x) {
		// TODO Auto-generated method stub
		
	}

}
