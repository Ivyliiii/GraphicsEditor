package JavaGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

// consists of points and connects the points
public class Pen extends Shape{
	int lineWidth = 1;
	Point s;
	ArrayList<Point> pen = new ArrayList<Point>();
	public Pen(Point square, Color c, int lw) {
		super(0,0,0,0,c);
		s = square;
		pen.add(square);
		lineWidth = lw;
	}

	@Override
	public Shape copy() {
		// TODO Auto-generated method stub
		return new Pen(s, c, lineWidth);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(lineWidth));		
		g.setColor(c);
		for(int i = 1; i < pen.size(); i++) {
			g.fillOval(pen.get(i).x, pen.get(i).y, pen.get(i).height, pen.get(i).height);
			g.drawLine(pen.get(i-1).x, pen.get(i-1).y, pen.get(i).x, pen.get(i).y);
		}
	}

	@Override
	public boolean isOn(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resize(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	public void addPoint(Point square) {
		pen.add(square);		
	}

}
