package edu.buffalo.cse116.code.model;


public class Mandelbrot extends Fractal{

	public Mandelbrot(){
		super();
		super.nameOfFractal = "Mandelbrot";
		xMin = -2.15;
		xMax = .6;
		yMin = -1.3;
		yMax = 1.3;
	}
	
	public double xPrime(double xCoord, double yCoord, Point curr) {
		double retVal = (xCoord*xCoord) - (yCoord*yCoord) +curr.x;
		return retVal;
	}

	public double yPrime(double xCoord, double yCoord, Point curr) {
		double retVal = (2*xCoord*yCoord) +curr.y;
		return retVal;
	}

}
