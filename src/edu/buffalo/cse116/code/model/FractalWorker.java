package edu.buffalo.cse116.code.model;

import javax.swing.SwingWorker;

import edu.buffalo.cse116.code.WorkerResult;

public class FractalWorker extends SwingWorker<WorkerResult, Void>{
	private Fractal current;
	private int topRow, bottomRow;
	private WorkerResult results;
	public FractalWorker(Fractal current,int upper, int lower){
		this.current = current;
		results = null;
		topRow = upper;
		bottomRow = lower;
	}
	
	@Override
	protected WorkerResult doInBackground() throws Exception {
		int[][]values = new int[bottomRow-topRow][2048];
		for(int i = 0;i<=values.length-1;i++){
			for(int y=0;y<values[i].length;y++){
				values[i][y] = current.pointGenerate(i+topRow, y);
			}			
		}
		results = new WorkerResult(topRow, values);
		return results;
	}
	

}
