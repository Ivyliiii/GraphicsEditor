package JavaGraphics;

import java.awt.Color;
import java.awt.Graphics;

// oval class 
public class Oval extends Shape{

	public Oval(int x, int y, int w, int h, Color c) {
		super(x, y, w, h, c);
	}

	public Shape copy() {
		return new Oval(x, y, width, height, c);
	}

	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, width, height);
	}

	public boolean isOn(int a, int b) {
		// functions to perform a equation found on line
		if(Math.pow(a-centerX(x, x+width),2)/Math.pow((width/2), 2) + Math.pow(b-centerX(y, y+height),2)/Math.pow((height/2), Math.pow((height/2), 2)) <= 1){
			return true;
		}
		return false;
	}
	
	//heller fucntions
	public int centerX(int x, int a) {
		return (int)(x+a)/2;
	}
	
	public int distance(int x, int y, int a, int b) {
		return (int)(Math.sqrt(Math.pow(x-a, 2) + Math.pow(y-b, 2)));
	}

	public void addPoint(Point x) {
		// TODO Auto-generated method stub
		
	}

	public void resize(int x1, int y1, int x2, int y2) {
		width = Math.abs(x1-x2);
		height = Math.abs(y1-y2);
		x = Math.min(x1, x2);
		y = Math.min(y1, y2);
		
	}

}
