package edu.buffalo.cse116.code.model;

import edu.buffalo.cse116.code.view.*;

import java.util.ArrayList;

import javax.swing.SwingWorker;

import edu.buffalo.cse116.code.*;

public class FractalModel {
	private Fractal selectedFractal; // CHANGE TO PRIVATE
	private UserInterface ui;
	private ComputePool cp;

	private int threads;

	//default fractal is Mandelbrot and escape is 2
	public FractalModel(){
		threads = 4;
		cp = new ComputePool();
		selectedFractal = new BurningShip();
		selectedFractal.setEscapeTime(2);
	}
	//sets the UI class
	public void setObserver(UserInterface ui){
		this.ui = ui;
		cp.changePanel(ui.getFractalPanel());
	}
	//generates the selected fractal
	public void generate(){
		ArrayList<SwingWorker<WorkerResult, Void>> workers = new ArrayList<SwingWorker<WorkerResult, Void>>();
		for(int i = 0;i<threads;i++){
			double division = 2048/threads;
			workers.add(new FractalWorker(selectedFractal, (int)(i*division),(int)((i+1)*division)));
		}
		cp.generateFractal(2048,workers);
	}
	//gets the escape value from the selected fractal
	public int getEscape(){
		return selectedFractal.getEscapeDist();
	}
	//gets the escape time from selected fractal @author Chris muya
	public int getEscapeTime(){
		return selectedFractal.getEscapeTime();
	}
	//gets the coordinate values
	public Point coordinateConvert(Point p){
		double deltaX = (selectedFractal.xMax - selectedFractal.xMin)/(ui.getFractalPanel().getSize().getWidth()-1);
		double deltaY = (selectedFractal.yMax - selectedFractal.yMin)/(ui.getFractalPanel().getSize().getWidth()-1);
		double newX = selectedFractal.xMin + (deltaX*p.x);
		double newY = selectedFractal.yMin + (deltaY*p.y);
		Point retval = new Point(newX, newY);
		return retval;
	}
	
	//sets and generates the mandelbrot fractal as the selected fractal
	public void mandelbrot(){
		cp.clearPool();
		int escpDist = getEscape();
		int escpTime = getEscapeTime();
		selectedFractal = new Mandelbrot();
		selectedFractal.setEscapeDistance(escpDist);
		selectedFractal.setEscapeTime(escpTime);
		ui.disableZoom();
		generate();
	}
	//sets and generates the julia fractal as the selected fractal
	public void julia(){
		cp.clearPool();
		int escpDist = getEscape();
		int escpTime = getEscapeTime();
		selectedFractal = new Julia();
		selectedFractal.setEscapeDistance(escpDist);
		selectedFractal.setEscapeTime(escpTime);
		ui.disableZoom();
		generate();
	}
	//sets and generates the burning ship fractal as the selected fractal
	public void burningShip(){
		cp.clearPool();
		int escpDist = getEscape();
		int escpTime = getEscapeTime();
		selectedFractal = new BurningShip();
		selectedFractal.setEscapeDistance(escpDist);
		selectedFractal.setEscapeTime(escpTime);
		ui.disableZoom();
		generate();
	}
	public void burningShipZoom(double xMin, double xMax, double yMin, double yMax){
		int escpDist = getEscape();
		int escpTime = getEscapeTime();
		selectedFractal = new BurningShip();
		selectedFractal.setEscapeDistance(escpDist);
		selectedFractal.setEscapeTime(escpTime);
		selectedFractal.changeCoordinates(xMin, xMax, yMin, yMax);
		generate();
	}
	//sets and generates the multibrot fractal as the selected fractal
	public void multibrot(){
		cp.clearPool();
		int escpDist = getEscape();
		int escpTime = getEscapeTime();
		selectedFractal = new Multibrot();
		selectedFractal.setEscapeDistance(escpDist);
		selectedFractal.setEscapeTime(escpTime);
		ui.disableZoom();
		generate();
	}
	//gets the escape values of the selected fractal
	public int[][] getPlane(){
		return selectedFractal.getPlane();
	}

	public void changeCoord(double xMin, double xMax, double yMin, double yMax) {
		this.selectedFractal.xMax = xMax;
		this.selectedFractal.xMin = xMin;
		this.selectedFractal.yMax = yMax;
		this.selectedFractal.yMin = yMin;
		
	}
	//sets the escape time that the user select
	public void setEscapeTime(int escTime) {
		cp.clearPool();
		selectedFractal.setEscapeTime(escTime);
		generate();
		
	}
	public void setEscapeDist(int escapeNew) {
		cp.clearPool();
		selectedFractal.setEscapeDistance(escapeNew);
		generate();
	}
	public void setNumThreads(int numberOfThreads) {
		threads = numberOfThreads;		
	}
	public int getNumThreads() {
		return threads;
	}
	public void changeCoordinates(double d, double e, double f, double g) {
		selectedFractal.changeCoordinates(d, e, f, g);
		
	}
	public String getName(){
		return selectedFractal.nameOfFractal;
	}

}
