
package edu.buffalo.cse116.code.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.IndexColorModel;
import java.util.ArrayList;
import java.awt.Graphics;
import javax.swing.*;
import edu.buffalo.cse116.code.model.*;
import edu.buffalo.cse116.code.listeners.*;

public class UserInterface extends JFrame {
    Point origin = null;
    private static IndexColorModel selectedColorModel;
    private FractalPanel fractalPanel;
    public FractalModel model;
    private static ArrayList<IndexColorModel> colorModels;
    edu.buffalo.cse116.code.model.Point firstClickPoint;
    edu.buffalo.cse116.code.model.Point secondClickPoint;
    
	//Don't touch these variables for Mouse	
	private int emX; 
	private int emY;
	
	//Don't touch these variables for drawn shape
	private int pointX;
	private int pointY;
	private int width;
	private int height; 
	
	//resetZoom button will be enabled after the zooming the fractal
	//it will be disable after resetting the fractal to its original coordinates
	JButton resetZoom;
	
	//this checks if the mouse was just clicked or if it was also dragged
	//this also fix bugs(the user might just click  without dragging which will NOT zoom and result in bugs and compiler errors)
	private boolean wasDragged;
    
    public UserInterface(){
    	model = new FractalModel();
    	createColorModels();
    	selectedColorModel = colorModels.get(1);
    	fractalPanel = new FractalPanel();    	
    	model.setObserver(this);
    	model.generate();
    	model.setEscapeTime(255);
    	resetZoom  = new JButton("Reset Zoom");//
    	resetZoom.setEnabled(false);
    	wasDragged = false;
    }
    //sets and refreshes, repaints, and updates the fractalPanel
    //we need something here after the user changes the number of threads
    public void updatePanel(){
    	
    	fractalPanel.setPreferredSize(new Dimension(2048, 2048));
    	
    	fractalPanel.setIndexColorModel(selectedColorModel);
    	model.generate();
    	fractalPanel.updateImage(model.getPlane()); 
    	
    	fractalPanel.repaint();
    }
    //creates the GUI window
    private void createAndShowGUI() {
        //Create and set up the window.       
        this.setTitle("Fractal Mania");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createAndAddMenuBar();
        fractalPanel.updateImage(model.getPlane());
        this.getContentPane().add(fractalPanel);
        //Display the window.
        this.repaint();
        /**
         * Cannot use .pack() or setSize to 2048 X 2048. This is because the window
         * that gets displayed is too big too properly appear on the screen.
         * However, the fractal size is still set to 2048 X 2048.
         * To conclude, the Fractal code calculates and display fractals in 2048 x 2048 pixels, 
         * but the JFrame size is set to any resolution that
         * can fit the user's screen resolution, as long as it's a perfect square e.g.(800 X 800) or (500 X 500)*/
        this.setSize(1000, 1000);
        this.setVisible(true);   
        this.setLocationRelativeTo(null);// this line makes the window appear on the center of the screen

        this.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent event){
        		origin = event.getPoint();       
                firstClickPoint = new edu.buffalo.cse116.code.model.Point(origin.getX(), origin.getY());                          
			}
			
			public void mouseReleased(MouseEvent e){				
				//erase the drawn shape				
				emX = 0;
				emY = 0;
				width = 0;
				height = 0;
				
				//this if-else statement below checks if the mouse was just clicked or if it was also dragged.
				//this also fix bugs(the user might just click  without dragging which will NOT zoom and result in bugs and compiler errors)
				if(!wasDragged){				
				    
				}else{
					double firstClickX = model.coordinateConvert(firstClickPoint).x;
				    double firstClickY = model.coordinateConvert(firstClickPoint).y;			        
				    double secondClickX = model.coordinateConvert(secondClickPoint).x;
				    double secondClickY = model.coordinateConvert(secondClickPoint).y;
				    
				    pointX = Math.abs(emX - origin.x);
				    pointY = Math.abs(emY - origin.y);  		        
				      
				    model.changeCoord(firstClickX, secondClickX, firstClickY, secondClickY);
				    model.changeCoordinates(minFinderX(firstClickX, secondClickX), maxFinderX(firstClickX, secondClickX),
				    minFinderY(firstClickY, secondClickY), maxFinderY(firstClickY, secondClickY));
				    model.generate();
				    fractalPanel.repaint();
				    repaint();
				    origin = null;
				    resetZoom.setEnabled(true);
				}
			}			
			
		});	    
	    
	    this.addMouseMotionListener(new MouseMotionAdapter(){
	    	public void mouseDragged(MouseEvent e){
	    		wasDragged = true;
	    		emX = e.getX();
	    		emY = e.getY();	
	    		
	    		secondClickPoint = new edu.buffalo.cse116.code.model.Point(e.getX(), e.getY()); 

	    		pointX = Math.min(origin.x, e.getX());
		        pointY = Math.min(origin.y, e.getY());
	    		width = Math.abs(e.getX() - origin.x);
	    		height = Math.abs(e.getY() - origin.y);
	    		
	    		repaint();
	    	}
	    });        
    } 
    
    public void disableZoom(){
    	resetZoom.setEnabled(false);
    }
    
    public FractalPanel getFractalPanel(){
    	return fractalPanel;
    }
    
    /**This method draws the shape on the frame*/
    public void paint(Graphics g) {    	
    		super.paint(g);
        	Graphics2D g2 = (Graphics2D) g;
        	g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(5));// thickness of the lines 	
            g2.drawRect(pointX, pointY, width, height);   
    }    
    
    /* This will create and add a navigational menu bar
     * File, Fractal, Color with functioning actions
     */    
    private void createAndAddMenuBar(){
    		JMenuBar openBar = new JMenuBar();
    	JMenu fileMenu = new JMenu("File");
    	JMenu fractalMenu = new JMenu("Fractal");
    	JMenu colorMenu = new JMenu("Color");
    	JMenu escapeMenu = new JMenu("Escape values");
    	JMenu threads = new JMenu("Threads");
    	ExitAction ea = new ExitAction();
    	JMenuItem quitter = new JMenuItem(ea);
    	JMenuItem excapeChange = new JMenuItem("Change Escape time..");
    	JMenuItem passesChange = new JMenuItem("Change Escape passes..");
    	JMenuItem setThreads = new JMenuItem("Set number of Threads");
    	JMenuItem blueButton = new JMenuItem("Blue");
    	JMenuItem greenButton = new JMenuItem("Green");
    	JMenuItem redButton = new JMenuItem("Red");
    	JMenuItem fuschiaButton = new JMenuItem("Fuschia");
    	JMenuItem multibrot = new JMenuItem("Multibrot");
    	JMenuItem mandelbrot = new JMenuItem("Mandelbrot");
    	JMenuItem julia = new JMenuItem("Julia");
    	JMenuItem burningShip = new JMenuItem("Burning Ship");
    	
    	ThreadsListener treadsListener = new ThreadsListener(this);
    	setThreads.addActionListener(treadsListener);    	
    	EscapeDistListener escapeListener = new EscapeDistListener(this);
    	excapeChange.addActionListener(escapeListener);
    	EscapeTimeListener passesListener = new EscapeTimeListener(this);
    	passesChange.addActionListener(passesListener);
    	
        //adds action listener to change selected color to blue
    	blueButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	changeColor(0);
    	    }
    	}); 
        
        //adds action listener to change selected color green
    	greenButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	changeColor(2);
    	    }
    	});
        
        //adds action listener to change selected color to red
    	redButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	changeColor(3);
    	    }
    	});
        
        //adds action listener to change selected color to fuschia
    	fuschiaButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	changeColor(1);
    	    }
    	});
        
        //adds action listener to change selected fractal to multibrot and repaint it
    	multibrot.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	model.multibrot();
    	    	
    	    }
    	});
        
        //adds action listener to change fractal to mandelbrot and repaint it
    	mandelbrot.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	model.mandelbrot();
    	    	
    	    }      
    	});
        
        //adds action listener to change selected fractal to julia and repaint it
        julia.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	model.julia();
    	    	
    	    }
    	});
        
        //adds action listener to change selected fractal to burning ship and repaint it
    	burningShip.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	model.burningShip();
    	    	
    	    }
    	});
      
      //adds action listener to recalculate and redisplay the fractal using the default coordinate range
    	resetZoom.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent ev) {
    	    	resetZoom.setEnabled(false);
    	    	
    	    	switch(model.getName()){
    	    		case "Julia":
    	    		  	model.changeCoord(-1.7, 1.7,-1.0, 1.0);
      			    	model.changeCoordinates(-1.7, 1.7,-1.0, 1.0);
      			    	model.generate();
      			    	fractalPanel.repaint();
    	    	    break;
    	    	    
    	    		case "BurningShip":
    	    			model.changeCoord(-1.8, -1.7,-.08, .025);
      			    	model.changeCoordinates(-1.8, -1.7,-.08, .025);
      			    	model.generate();
      			    	fractalPanel.repaint();
    	    	    break;
    	    	    
    	    		case "Multibrot":
    	    			model.changeCoord(-1, 1, -1.3, 1.3);
      			    	model.changeCoordinates(-1, 1, -1.3, 1.3);
      			    	model.generate();
      			    	fractalPanel.repaint();
      	    	    break;
      	    	    
    	    		case "Mandelbrot":
    	    			model.changeCoord(-2.15, .6, -1.3, 1.3);
      			    	model.changeCoordinates(-2.15, .6, -1.3, 1.3);
      			    	model.generate();
      			    	fractalPanel.repaint();
      	    	    break;    	    	
    	    	}    	    	
    	    }
    	});
    	
    	//adds all menu items to menu
    	fileMenu.add(quitter);
    	fractalMenu.add(burningShip);
    	fractalMenu.add(julia);
    	fractalMenu.add(mandelbrot);
    	fractalMenu.add(multibrot);
    	colorMenu.add(blueButton);
    	colorMenu.add(fuschiaButton);
    	colorMenu.add(greenButton);
    	colorMenu.add(redButton);
    	escapeMenu.add(excapeChange);
    	escapeMenu.add(passesChange);
    	threads.add(setThreads);
        //adds menus to menubar
    	openBar.add(fileMenu);
    	openBar.add(fractalMenu);
    	openBar.add(colorMenu);
    	openBar.add(escapeMenu);
    	openBar.add(threads);
    	openBar.add(resetZoom);    	
        //adds menubar to jframe
    	this.setJMenuBar(openBar);
    }
    
    //creates an escape dialog box to change the escape distance
    public void escapeDistDialog(){
    	try{
    	int escapeNew = Integer.parseInt((String) JOptionPane.showInputDialog(this,
    	        "Enter a new escape distance,\nAn integer from 1-255",
    	        "New Escape Distance", JOptionPane.INFORMATION_MESSAGE,
    	        null,
    	        null,
    	        model.getEscape()));    	
    	model.setEscapeDist(escapeNew);
    	if(escapeNew!=model.getEscape()){throw new NumberFormatException();}
    	updatePanel();
    	JOptionPane.showMessageDialog(this, "Escape distance successfully changed to: "+model.getEscape());    	
    	} catch(NumberFormatException e){try{
    		JOptionPane.showMessageDialog(this, "Escape distance unsuccessfully changed.");} catch(NumberFormatException d){}
    	}
    	this.repaint();
    }
    
    public void escapeTimeDialog(){
    	try{
    	int escapeTimeNew = Integer.parseInt((String) JOptionPane.showInputDialog(this,
    	        "Enter a new escape Time,\nAn integer from 1-255",
    	        "New Escape Time", JOptionPane.INFORMATION_MESSAGE,
    	        null,
    	        null,
    	        model.getEscapeTime() ));//escape distance
    	model.setEscapeTime(escapeTimeNew);
    	if(escapeTimeNew!=model.getEscapeTime()){throw new NumberFormatException();}
    	updatePanel();
    	JOptionPane.showMessageDialog(this, "Escape Time successfully changed to: "+model.getEscapeTime());    	
    	} catch(NumberFormatException e){try{
    		JOptionPane.showMessageDialog(this, "Escape Time unsuccessfully changed.");} catch(NumberFormatException d){}
    	}
    	this.repaint();
    }
    
    public void threadsDialog(){
    	try{
    	int numberOfThreads = Integer.parseInt((String) JOptionPane.showInputDialog(this,
    	        "Enter the number of Thread(s),\nAn integer from 1-128",
    	        "Number of Thread(s)", JOptionPane.INFORMATION_MESSAGE,
    	        null,
    	        null,
    	        model.getNumThreads() ));//escape distance
    	model.setNumThreads(numberOfThreads);
    	
	    fractalPanel.repaint();
    	if(numberOfThreads < 1 || numberOfThreads > 128 ){throw new NumberFormatException();}
    	updatePanel();
    	JOptionPane.showMessageDialog(this, "Number of Thread(s) successfully changed to: " + model.getNumThreads());    	
    	} catch(NumberFormatException e){try{
    		JOptionPane.showMessageDialog(this, "Number of Thread(s) unsuccessfully changed.");} catch(NumberFormatException d){}
    	}
    	//this.repaint();
    }
    
    public void run() {
        createAndShowGUI();
    }
    
    //creates and adds IndexColorModels in the order - Blue, Fuchsia, Green, Red
    public static void createColorModels(){
    	ColorModelFactory fact = new ColorModelFactory();
    	colorModels = new ArrayList<IndexColorModel>();
    	colorModels.add(fact.createBluesColorModel(500));
    	colorModels.add(fact.createFuchsiaColorModel(500));    	
    	colorModels.add(fact.createGreensColorModel(500));
    	colorModels.add(fact.createRedsColorModel(500));
    }
    
    //method to change color of fractal from IndexColorModel arraylist
    public void changeColor(int index){
    	selectedColorModel = colorModels.get(index);
    	updatePanel();
    	fractalPanel.repaint();
    	this.repaint();
    }

    public double maxFinderX(double x1, double x2) {
    	if (x1>x2) {return x1;}
    	else {return x2;}
    }
    
    public double minFinderX(double x1, double x2) {
    	if (x1>x2) {return x2;}
    	else {return x1;}
    }
    
    public double maxFinderY(double y1, double y2) {
    	if (y1>y2) {return y1;}
    	else {return y2;}
    }
    
    public double minFinderY(double y1, double y2) {
    	if (y1>y2) {return y2;}
    	else {return y1;}
    }
   
}
