//Based off FractalTest2
package edu.buffalo.cse116.tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import edu.buffalo.cse116.code.model.*;

public class fractalTests {
	Multibrot _mb;
	Julia _j;
	Mandelbrot _m;
	BurningShip _b;
	Point p = new Point(0,0);
	//Variables for the coordinate values of each point for each fractal
	double _mcoordx_1 = 0.3207031250000001;
	double _mcoordy_1 = -0.07109374999999386;
	double _jcoordx_1 = 1.0492187499999897;
	double _jcoordy_1 = -0.234375;
	double _mbcoordx_1 = 0.5859375;
	double _mbcoordy_1 = 0.24375000000000108;
	double _bcoordx_1 = -1.7443359374999874;
	double _bcoordy_1 = -0.017451171875000338;
	
	double _mbcoordx_2 = 0.7025440313111545;
	double _mbcoordy_2 = -0.5520547945205528;
	double _jcoordx_2 = 1.4538160469667272;
	double _jcoordy_2 = -0.13502935420743645;
	double _mcoordx_2 = 0.46007827788650374;
	double _mcoordy_2 = -0.3383561643835661;
	double _bcoordx_2 = -1.6999999999999802;
	double _bcoordy_2 = 0.0030136986301371603;
	
	double _mbcoordx_3 = 0.5859375;
	double _mbcoordy_3 = .24375000000000108;
	
	double _jcoordx_3 = 1.0492187499999897;
	double _jcoordy_3 = -0.234375;
	
	double _mcoordx_3 = 0.3207031250000001;
	double _mcoordy_3 = -0.07109374999999386;
	
	double _bcoordx_3 = -1.7443359374999874; 
	double _bcoordy_3 =-0.017451171875000338;
	
	//Tests that test for escape time for a coordinate whose distance never exceeds the escape distance
	@Before
	// Just creating instances of each fractal class
	public void initialize() {
		_mb = new Multibrot();
		_b = new BurningShip();
		_j = new Julia();
		_m = new Mandelbrot();
	}
	
	
	//TESTS FROM PHASE 1
	
	
	@Test 
	public void testMandelbrot1() {
		assertEquals(_m.coordinateEscape(_mcoordx_1, _mcoordy_1), 255, .1); 
	}
	
	@Test
	public void testJulia1() {
		assertEquals(_j.coordinateEscape(_jcoordx_1, _jcoordy_1), 255, .1); 
	}
	@Test
	public void testBurningShip1() {
		assertEquals(_b.coordinateEscape(_bcoordx_1, _bcoordy_1), 255, .1); 
	}
	@Test
	public void testMultiBrot() {
		assertEquals(_mb.coordinateEscape(_mbcoordx_1, _mbcoordy_1), 255, .1); 
	}
	@Test 
	public void testMandelbrot() {
		assertEquals(1.0, _m.coordinateEscape(0.5946289062500001, 1.2949218750000122), 0.0); 
	}
	@Test
	public void testJulia() {
		assertEquals(1.0, _j.coordinateEscape(1.6933593749999853, 0.9765625), 0.0); 
	}
	@Test
	public void testMultibrot() {
		assertEquals(1.0, _mb.coordinateEscape(0.9921875, 1.05625), 0.0);
	}
	@Test
	public void testBurningShip() {
		_b.fractalGenerate();
		
		for (int i = 0; i < 512; i++) {
			  for (int j = 0; j < 512; j++) {
			    assertTrue(_b.getPlane()[i][j] != 0);
			    assertNotEquals(_b.getPlane()[i][j],1);
			  }
			}
	}
	@Test
	public void translatePixelRowX(){
		//for Mandelbrot
		double xm = _m.getCoordinatevalues(p).x;		
		assertEquals(-2.15, xm, 0.1);
		//for Julia
		double xj = _j.getCoordinatevalues(p).x;		
		assertEquals(-1.7, xj, 0.1);
		//forBurningship
		double xb = _b.getCoordinatevalues(p).x;		
		assertEquals(-1.8, xb, 0.1);
		//for Multibrot
		double xmu =  _mb.getCoordinatevalues(p).x;		
		assertEquals(-1, xmu, 0.1);		
	}
	
	//Translate a pixel's column to the associated y-coordinate in the fractal (1 test per fractal)
	@Test
	public void translatePixelRowY(){
		//for Mandelbrot
		double xm = _m.getCoordinatevalues(p).y;		
		assertEquals(-1.3, xm, 0.1);
		//for Julia
		double xj = _j.getCoordinatevalues(p).y;		
		assertEquals(-1.0, xj, 0.1);
		//forBurningship
		double xb = _b.getCoordinatevalues(p).y;		
		assertEquals(-.08, xb, 0.1);
		//for Multibrot
		double xmu =  _mb.getCoordinatevalues(p).y;		
		assertEquals(-1.3, xmu, 0.1);		
	}
	
	//The method called to calculate the fractal returns a 2-d array with 512 rows and 512 columns (1 test per fractal)
	@Test
	public void return2Darray(){
		//this calculate that the BurningShip returns a 2D array with 512 rows and columns
		_b.fractalGenerate();
		int rows = _b.getPlane().length;
		int cols = _b.getPlane()[0].length;
		assertEquals(rows,2048);
		assertEquals(cols,2048);
		
		//this calculate that the Julia returns a 2D array with 512 rows and columns
		_j.fractalGenerate();
		int rowsJ = _j.getPlane().length;
		int colsJ = _j.getPlane()[0].length;
		assertEquals(rowsJ,2048);
		assertEquals(colsJ,2048);
		
		//this calculate that the Mandelbrot returns a 2D array with 512 rows and columns
		_m.fractalGenerate();
		int rowsM = _m.getPlane().length;
		int colsM = _m.getPlane()[0].length;
		assertEquals(rowsM,2048);
		assertEquals(colsM,2048);
		
		//this calculate that the Mystery returns a 2D array with 512 rows and columns
		_mb.fractalGenerate();
		int rowsMy = _mb.getPlane().length;
		int colsMy = _mb.getPlane()[0].length;
		assertEquals(rowsMy,2048);
		assertEquals(colsMy,2048);
	}
	
	
	//TESTS FROM PHASE 2
	
	
	@Test 
	public void testMandelbrot2() {
		_m.setEscapeDistance(3);
		assertEquals(_m.coordinateEscape(_mcoordx_2, _mcoordy_2), 10, .1); 
		}
	
	@Test
	public void testJulia2() {
		_j.setEscapeDistance(3);
		assertEquals(_j.coordinateEscape(_jcoordx_2, _jcoordy_2), 10, .1); 	
	}
	
	@Test
	public void testBurningShip2() {
		_b.setEscapeDistance(3);
		assertEquals(_b.coordinateEscape(_bcoordx_2, _bcoordy_2), 10, .1); 
	}
	
	@Test
	public void testMultiBrot2() {
		_mb.setEscapeDistance(3);
	assertEquals(_mb.coordinateEscape(_mbcoordx_2, _mbcoordy_2), 10, .1); 
	}
	
	
	//TESTS FROM PHASE 3
	
	@Test 
	public void testMandelbrot3() {
		_m.setEscapeDistance(2);
		_m.setEscapeTime(135);
		assertEquals(_m.coordinateEscape(_mcoordx_3, _mcoordy_3), 135, .1); 
		}
	
	@Test
	public void testJulia3() {
		_j.setEscapeDistance(2);
		_j.setEscapeTime(135);
		assertEquals(_j.coordinateEscape(_jcoordx_3, _jcoordy_3), 135, .1); 	
	}
	
	@Test
	public void testBurningShip3() {
		_b.setEscapeDistance(2);
		_b.setEscapeTime(135);
		assertEquals(_b.coordinateEscape(_bcoordx_3, _bcoordy_3), 135, .1); 
	}
	
	@Test
	public void testMultiBrot3() {
		_mb.setEscapeDistance(2);
		_mb.setEscapeTime(135);
	assertEquals(_mb.coordinateEscape(_mbcoordx_3, _mbcoordy_3), 135, .1); 
	}
}