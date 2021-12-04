package JavaGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
// i have tried to solve for the closest distance between a point and line, but I don't think I did it correctly
public class Line extends Shape{
	int lineWidth = 10;
	public Line(int x, int y, int x2, int y2, Color c, int l) {
		super(x, y, x2, y2, c);
		lineWidth = l;
	}

	public Shape copy() {
		return new Line(x, y, width, height, c, lineWidth);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(lineWidth));
		g.drawLine(x, y, width, height);
	}

	public boolean isOn(int a, int b) {
		int slopePer = findPerpenSlope(x, y, a, b);
		int constantPer = findConstant(slopePer);
		int slopeL = -1/slopePer;
		int constantL = findConstant(slopeL);
		int x_c = solveX(slopePer, constantPer, slopeL, constantL);
		int y_c = solveY(x_c, slopePer, constantPer);
		if(distance(a, b, x_c, y_c) < 10) {
			return true;
		}
		return false;
	}

	public int findPerpenSlope(int x1, int y1, int x2, int y2) {
		return findConstant(-1 * (x2-x1)/(y2-y1));
	}
	
	public int findConstant(int slope) {
		return y-slope*x;
	}
	
	public int solveX(int s, int k, int s2, int k2) {
		return (int)(k2-k)/(s-s2);
	}
	
	public int solveY(int x_c, int s2, int k2) {
		return (int)x_c*s2 + k2;
	}

	
	public int distance(int x, int y, int a, int b) {
		return (int)(Math.sqrt(Math.pow(x-a, 2) + Math.pow(y-b, 2)));
	}
	
	public void resize(int x1, int y1, int x2, int y2) {
		x = x1;
		y = y1;
		width = x2;
		height = y2;
	}
	
	public void addPoint(Point x) {
		// TODO Auto-generated method stub
		
	}

}

