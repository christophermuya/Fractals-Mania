package edu.buffalo.cse116.code.model;

import java.lang.Math;

public class BurningShip extends Fractal {
	
	public BurningShip(){
		super();
		super.nameOfFractal = "BurningShip";
		xMin = -1.8;
		xMax = -1.7;
		yMin = -.08;
		yMax = .025;
	}
	
	public double xPrime(double xCoord, double yCoord, Point curr) {
		double retVal = (xCoord*xCoord) - (yCoord*yCoord) + curr.x;
		return retVal;
	}

	public double yPrime(double xCoord, double yCoord, Point curr) {
		double retVal = Math.abs(2*xCoord*yCoord)+curr.y;
		return retVal;
	}
	

}
