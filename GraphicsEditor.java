package JavaGraphics;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
public class GraphicsEditor{	
	private final int height = 600, width = 800;
	int resize = -1; // to keep track of the shape that we are dragging, if none are being resized, it will be set to -1
	int current_x, current_y; // keeps track of the current location of the mouse while being dragged
	int mode = 1; // keeps track of the mode that the editor is in
	private JTextArea typeTextArea, displayTextInput, textSizeDisplay; // a bunch of text boxes
	private JTextArea textSizeArea, textSizeInput;
	private JTextArea LineSizeArea, LineSizeInput;
	private JTextArea EraserSizeInput, EraserSizeArea;
	JPanel canvas; // main place to keep track of 
	JFrame frame;
	int pressed_x, pressed_y, released_x, released_y; // the starting location of the mouse being pressed and released
	int clicked_x, clicked_y; // clicked locations
	int shape_l = 20, shape_w = 20; 
	int textSize = 20; 
	int LineWidth = 10;
	int EraserWidth = 10;
	int d_x, d_y;
	boolean newPen = true; 
	boolean newEraser = true;
	String str = ""; 
	ArrayList<ArrayList<Shape>> states = new ArrayList<ArrayList<Shape>>(); // this will keep track of the different states that the editor was in for undo and redo
	ArrayList<ArrayList<Shape>> redo = new ArrayList<ArrayList<Shape>>(); // basically the same thing as states
 	ArrayList<Shape> shapes = new ArrayList<Shape>(); // where every shape on the screen is kept
	Color color = Color.BLACK; //default color for shapes
	Color bg = Color.WHITE;

	public GraphicsEditor() {
		frame = new JFrame();
		frame.setSize(width, height); // initiating all the JTextAreas
		textSizeDisplay = new JTextArea();
		LineSizeInput = new JTextArea();
		typeTextArea = new JTextArea();
		displayTextInput = new JTextArea();
		textSizeInput = new JTextArea();
		LineSizeArea = new JTextArea();
		textSizeArea = new JTextArea();
		EraserSizeInput = new JTextArea();
		EraserSizeArea = new JTextArea();
		
		JPanel container = new JPanel(); // the main box for different stuff
		container.setBackground(new Color(241, 245, 220));
		
		// to make the layout vertical
		BoxLayout boxlayout = new BoxLayout(container, BoxLayout.Y_AXIS);
		container.setLayout(boxlayout);
		
		// this will contain the buttons
		JPanel topUser = new JPanel();
		topUser.setPreferredSize(new Dimension(width, height/8));
		topUser.setBackground(new Color(207, 205, 204));
		
		canvas = new JPanel() {
			// this will be the main paint method that will draw out all of the shapes in the list
			public void paint(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, 5*height/6);
				
				g.setColor(color);
				for(Shape s : shapes) {
					s.draw(g);
				}
				
				if(mode == 5) { // clear and this will get away everything
					g.setColor(Color.WHITE);
					shapes = new ArrayList<Shape>();
					g.fillRect(0, 0, width, 500);
					shapes = new ArrayList<Shape>();
				}
			}
		};
		
		canvas.setPreferredSize(new Dimension(width, 6*height/8));
		
		// declaring the buttons
		JButton rectButton = new JButton("Rectangle");
		rectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 1;
			}
		});
		
		JButton colorChooser = new JButton("Choose Color");
		colorChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = JColorChooser.showDialog(canvas, "Select a color", Color.WHITE);
			}
		});
		
		JButton textButton = new JButton("Text");
		textButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 3;
			}
		});
		
		typeTextArea.setEditable(true);
		typeTextArea.setLineWrap(true);
		
		JButton ovalButton = new JButton("Oval");
		ovalButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 2;
			}
		});
		
		JButton circleButton = new JButton("Circle");
		circleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 6;
			}
		});
		
		JButton lineButton = new JButton("Line");
		lineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 4;
			}
		});
		
		JButton penButton = new JButton("Pen");
		penButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 12;
			}
		});
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 5;
				frame.getContentPane().repaint();
			}
		});
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 7;
			}
		});
		
		JButton eraserButton = new JButton("Eraser");
		eraserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 17;
			}
		});

		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// once it is undoed, the state will be added to the redo list
				redo.add(states.get(states.size()-1));
				states.remove(states.size()-1);
				// sometimes we need to click twice to get rid of one shape and i worte this, but it didn't seem to work
				if(shapes == states.get(states.size()-2)) {
					shapes = states.get(states.size()-3);
					redo.add(states.get(states.size()-2));
				}
				else {
					shapes = states.get(states.size()-2);
					redo.add(states.get(states.size()-1));
				}
				frame.getContentPane().repaint(); // repaints after shape list is changed
			}
		});
		
		// it only redos once
		JButton redoButton = new JButton("Redo");
		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gets shape list from previous undo list
				shapes = redo.get(redo.size()-2);
				frame.getContentPane().repaint(); // repaints again
			}
		});
		
		// more buttons....
		JButton toFrontButton = new JButton("Bring To Front");
		toFrontButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 8;
			}
		});
		
		JButton toBackButton = new JButton("Bring To Back");
		toBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mode = 9;
			}
		}); 
		
		JButton moveButton = new JButton("Move");
		moveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mode = 15;
			}
		});
		
		JButton saveButton = new JButton("Save"); // this code was copied from online (stackoverflow)
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = img.createGraphics();
				canvas.printAll(g2d);
				g2d.dispose();
				try {
				    ImageIO.write(img, "png", new File("save.png"));
				} catch (IOException ex) {
				    ex.printStackTrace();
				}
			}
		});
		
		// more text display stuff
		textSizeDisplay.setText("Text Size:");
		textSizeDisplay.setEditable(false);
		textSizeInput.setEditable(true);
		textSizeInput.setText("   ");
		textSizeInput.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				getTextSize();
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		});
		
		textSizeArea.setEditable(true);
		displayTextInput.setText("Text Content:");
		displayTextInput.setEditable(false);
		typeTextArea.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				getString();
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		});
		
		LineSizeArea.setText("Line Width:");
		LineSizeArea.setEditable(false);
		LineSizeInput.setEditable(true);
		LineSizeInput.setText("   ");
		LineSizeInput.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				getLineSize();
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		});
		
		EraserSizeArea.setText("Eraser Width:");
		EraserSizeArea.setEditable(false);
		EraserSizeInput.setEditable(true);
		EraserSizeInput.setText("   ");
		EraserSizeInput.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				getEraserSize();
			}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
		});
		
		canvas.addMouseListener(new MouseListener() {
			// this function will happen if mouse is clicked
			public void mouseClicked(MouseEvent e) {
				clicked_x = e.getX();
				clicked_y = e.getY();
				if (mode == 7) { // this is delete function
					for(int i = shapes.size()-1; i >= 0; i--) {
						if(shapes.get(i).isOn(clicked_x, clicked_y)) { // if the part clicked is on the shape, it will be removed
 							shapes.remove(i); 
							frame.getContentPane().repaint();
							break; // it will only delete the shape on the top
						}
					}
				}
				else if(mode == 8) { // to front
					for(int i = shapes.size()-1; i >= 0; i--) {
						if(shapes.get(i).isOn(clicked_x, clicked_y)) { // if it is clicked on the shape
							shapes.add(shapes.size()-1, shapes.remove(i)); // the shape will be reoved and added to the very end of the list
							break; 
						}
					}
				}
				else if(mode == 9) { // to back
					for(int i = shapes.size()-1; i >= 0; i--) {
						if(shapes.get(i).isOn(clicked_x, clicked_y)) { // same thing as previous
							shapes.add(0,shapes.remove(i)); // only it will added to the front of the list
							break;
						}
					}
				}
				else if(mode == 3) { // text
					getTextSize(); // see below, gets the input from the JTextArea
					getString(); // gets the string input from the JTextArea
					shapes.add(new Text(clicked_x, clicked_y, str, color, textSize)); // adds a text to the shape list
				}
				undo(); // undo will save the state of the shapes into the states list
			}
			
			public void mousePressed(MouseEvent e) {
				// when the mouse is pressed
				// many of shapes will have width and height that states at 0, but they change according to place of the dragged mouse
				pressed_x = e.getX();
				pressed_y = e.getY();
				if(mode == 1) { // adds a rectangle to the shape list
					shapes.add(new Rect(pressed_x,pressed_y, 0,0, color));
				}
				else if(mode == 2) { // adds a oval to the shape list
					shapes.add(new Oval(pressed_x, pressed_y, 0, 0, color));
				}
				else if(mode == 4) { // adds a line to the shape list
					getLineSize(); // gets the linewidth from the user input
					shapes.add(new Line(pressed_x, pressed_y, 0, 0, color, LineWidth));
				}
				else if(mode == 6) { // adds a circle
					shapes.add(new Circle(pressed_x, pressed_y, 0, color));
				}
				else if(mode == 15) { // move function
					for(int i = 0; i < shapes.size(); i++) {
						if(shapes.get(i).isOn(pressed_x, pressed_y)) { 
							d_x = pressed_x - shapes.get(i).x; // finds the distance between the point selected on the shape to the top left corner of the shape
							d_y = pressed_y - shapes.get(i).y; // saves all of these values for later
							break; // only gets on shape
						}
					}
				}
				undo(); // saves the current state for redo and undo
			}

			public void mouseReleased(MouseEvent e) {
				// runs if the mouse is released
				released_x = e.getX();
				released_y = e.getY();
				undo();
				// not much here but just to save the state of the shapes when something has happened
				frame.getContentPane().repaint();
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		
		canvas.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				current_x = e.getX();
				current_y = e.getY();
				if(mode != 3 && mode != 12 && mode != 15 && mode != 17) { 
					// this will happen if a shape is first created, it will reshape according to the mouse location
					shapes.get(shapes.size()-1).resize(pressed_x, pressed_y, current_x, current_y);
				}
				// this is the pen motion, which will first create new a new pen shape when first drawn and first consists of a single point
				else if(mode == 12 && newPen) {
					getLineSize();
					shapes.add(new Pen(new Point(current_x, current_y, color), color, LineWidth));
				}
				// basically the samething as above
				else if(mode == 17 && newEraser) {
					getEraserSize();
					shapes.add(new Pen(new Point(current_x, current_y, bg), bg, EraserWidth));
				}
				
				// if it is not new, the it will addpoint
				else if (mode == 17 && !newEraser) {
					shapes.get(shapes.size()-1).addPoint(new Point(current_x, current_y, bg));
				}
				
				else if(mode == 15) { // move function
					for(int i = shapes.size()-1; i >= 0; i--) {
						if(shapes.get(i).isOn(current_x, current_y)) { // takes the distance found previously, the new left most point is plugged in, and the width and height are added to it
							shapes.get(i).resize(current_x-d_x, current_y-d_y, current_x-d_x+shapes.get(i).width, current_y-d_y+shapes.get(i).height);
						}
					}
				}
				// this is the continuation of pen which adds a new point
				else if(mode == 12 && !newPen) {
					shapes.get(shapes.size()-1).addPoint(new Point(current_x, current_y, color));
				}
				frame.getContentPane().repaint();
				newPen = false;
				newEraser = false;
			}
			
			// if the mouse is released, a new pen would be created
			public void mouseMoved(MouseEvent e) {
				newPen = true;
				newEraser = true;
			}
	
		});
		
		// all of the buttons and text areas are added to the top part of the screen
		frame.setSize(width, height);
		topUser.add(rectButton);
		topUser.add(circleButton);
		topUser.add(ovalButton);
		topUser.add(colorChooser);
		topUser.add(lineButton);
		topUser.add(penButton);
		topUser.add(LineSizeArea);
		topUser.add(LineSizeInput);
		topUser.add(clearButton);
		topUser.add(deleteButton);
		topUser.add(toFrontButton);
		topUser.add(toBackButton);
		topUser.add(eraserButton);
		topUser.add(EraserSizeArea);
		topUser.add(EraserSizeInput);
		topUser.add(moveButton);
		topUser.add(textButton);
		topUser.add(displayTextInput);
		topUser.add(typeTextArea);
		topUser.add(textSizeDisplay);
		topUser.add(textSizeInput);
		topUser.add(saveButton);
		topUser.add(undoButton); 
		topUser.add(redoButton);
		container.add(topUser);
		container.add(canvas);
		frame.add(container);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}	
	
	// a function that gets the line width of pen and line
	public void getLineSize() {
		if (!LineSizeInput.getText().trim().equals("")) {
			LineWidth = Integer.valueOf(LineSizeInput.getText().trim());
		}
		else {
			LineWidth = 1; //if the input box is empty, the line size is set to be 1
		}
	}
	
	// gets the size of the eraser
	public void getEraserSize() {
		if(!EraserSizeInput.getText().trim().equals("")) {
			EraserWidth = Integer.valueOf(EraserSizeInput.getText().trim());
		}
		else {
			EraserWidth = 10; // eraser size is 5 automatically
		}
	}
	
	// this will get the text size from the textarea
	public void getTextSize() {
		if (!textSizeInput.getText().trim().equals("")) {
			textSize = Integer.valueOf(textSizeInput.getText().trim());
		}
		else {
			textSize = 10; //if the input for text size is empty, the size is set automatically to 10
		}
	}
	
	// get the text of the printed text
	public void getString() {
		if (!typeTextArea.getText().trim().equals("")) {
			str = typeTextArea.getText().trim();
		}
	}
	
	// this will save a snapshot of the current shape
	public void undo() {
		ArrayList<Shape> temp = new ArrayList<Shape>();
		for(int i = 0; i < shapes.size(); i++) {
			temp.add(shapes.get(i).copy()); 
			// this is necessary because when shape is simply added
			// to the states list, the state components will change according to the 
			//shape, which means this function will simply perform nothing
		}
		states.add(temp);
	}

	public static void main(String[] args) {
		new GraphicsEditor();
	}
}
