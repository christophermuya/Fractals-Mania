package edu.buffalo.cse116.code.model;


public class Multibrot extends Fractal {

	public Multibrot(){
		super();
		super.nameOfFractal = "Multibrot";
		xMin = -1;
		xMax = 1;
		yMin = -1.3;
		yMax = 1.3;
	}
	
	public double xPrime(double xCoord, double yCoord, Point curr) {
		double retval = Math.pow(xCoord, 3) - (3*xCoord*(Math.pow(yCoord, 2))) + curr.x;
		return retval;
	}

	public double yPrime(double xCoord, double yCoord, Point curr) {
		double retval = (3*xCoord*xCoord*yCoord) - Math.pow(yCoord, 3)+curr.y;
		return retval;
	}
	

}
