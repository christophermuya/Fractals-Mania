package edu.buffalo.cse116.code.model;


public class Julia extends Fractal  {
	
	public Julia(){
		super();
		super.nameOfFractal = "Julia";
		xMin = -1.7;
		xMax = 1.7;
		yMin = -1.0;
		yMax = 1.0;		
	}
	
	public double xPrime(double xCoord, double yCoord, Point curr) {
		double retVal = (xCoord*xCoord) - (yCoord*yCoord) - 0.72689;
		return retVal;
	}

	public double yPrime(double xCoord, double yCoord, Point curr) {
		double retVal = (2*xCoord*yCoord) +0.188887;
		return retVal;
	}

}
