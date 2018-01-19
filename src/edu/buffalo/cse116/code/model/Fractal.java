package edu.buffalo.cse116.code.model;

import java.lang.Math;

public abstract class Fractal {
	private int[][] plane;
	public abstract double xPrime(double xCoord, double yCoord, Point curr);
	public abstract double yPrime(double xCoord, double yCoord, Point curr);
	protected double xMin, xMax, yMin, yMax;

	private int escapeDist;
	private int escapeTime;
	
	public String nameOfFractal;// need to know the fractal for the resetZoom button
	
	public Fractal(){
		escapeDist = 2;
		escapeTime = 255;
		plane = new int[2048][2048];
	}
	
	/* Method that returns the distance from the doubles in the parameter list to
	 * the origin
	 */
	public double pythagoreansF(double x, double y){
		return Math.sqrt((x*x)+(y*y));
	}
	
	/* Method that returns the value at the input x and y position in the array
	 * The parameter values are the position in the 2-D array NOT the cartesian plane
	 */
	public int getEscapeValue(int x, int y){
		return plane[x][y];
	}
	
	/* Method that returns the whole array of points
	 */
	public int[][] getPlane(){
		return plane;
	}
	
	/* Method that runs through each position in the 2-D array and calculates the escape
	 * time of the fractal.
	 */
	public void rowGenerate(int row){
		setEscapeDistance(escapeDist);
			for(int y = 0; y<plane[row].length; y++){
				plane[row][y] = pointGenerate(row, y);
			}
		
	}
	public int pointGenerate(int row,int col){
				Point p = new Point(row, col);
				p = coordinateCalc(p);
				return fractalCalculate(p.x, p.y);		
	}
	public void fractalGenerate(){
		for(int i = 0; i< plane.length;i++){
			rowGenerate(i);
		}
	}
	/*Method that sets escape time of the fractal to be generated
	 * on the next call to be generated.
	 */	
	public void setEscapeDistance(int escp){
		if(escp > 255){}
		else if(escp<1){}
		else{
		this.escapeDist = escp;}
	}
	
	public void setEscapeTime(int escTime){
		if(escTime>255){}
		else if(escTime<1){}
		else{
		this.escapeTime = escTime;}
	}
	
	/* Returns the true coordinate 
	 * values of the input points
	 */
	public Point getCoordinatevalues(Point p){
		Point retval = coordinateCalc(p);
		return retval;
	}
	
	/* Will take in a single coordinate input and output its escape time(ie passes)
	 * Must be input the true coordinates (IE the true x and y values, NOT the 2-d array values)
	 */
	public int coordinateEscape(double xVal, double yVal){
		return fractalCalculate(xVal, yVal);
	}
	
	/*Takes the position on the 2-D array of pixels and translates that into the true X & Y
	 * coordinates on the cartesian plane.
	 * 
	 */
	private Point coordinateCalc(Point p){
		double deltaX = (xMax - xMin)/2047;
		double deltaY = (yMax - yMin)/2047;
		double newX = xMin + (deltaX*p.x);
		double newY = yMin + (deltaY*p.y);
		Point retval = new Point(newX, newY);
		return retval;
	}
	
	public int getEscapeDist(){
		return escapeDist;
	}
	public int getEscapeTime(){
		return escapeTime;
	}
	
	public int getMaxPasses(){
		return escapeTime;
	}
	
	
	/* Method that calculates the escape time values of 
	 * the input position from the fractalGenerate method
	 */
	private int fractalCalculate(double xVal, double yVal){
		Point p = new Point(xVal, yVal);
		double xCalc = xVal;
		double yCalc = yVal;
		double dist = pythagoreansF(p.x, p.y);
		int passes = 0;
		while(dist<=this.escapeDist && passes<escapeTime){
			double xCoord = xCalc;
			double yCoord = yCalc;
			xCalc = xPrime(xCoord, yCoord, p);
			yCalc = yPrime(xCoord, yCoord, p);
			passes++;
			dist = pythagoreansF(xCalc, yCalc);
		}
		return passes;
	}
	
	public void changeCoordinates(double xMin,double xMax,double yMin,double yMax) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}

}
