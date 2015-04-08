package bc.diffusion.main;

import java.util.Random;

import bc.diffusion.chemicals.Chemical;
import bc.diffusion.equations.URate;
import bc.diffusion.equations.VRate;

/**
 * This class is used for tracking and updating almost all of the relevant simulation data. It keeps track of both
 * U and V chemical concentrations for the entire grid as well as updating this data at each time step. It tracks
 * and updates the distribution of the concentrations for each chemical (from 0.0 to 1.0) in the grid and also the 
 * data for the average concentration of each chemical for the entire grid.
 * 
 * @author Brennan Collins
 *
 */
public class Grids {

	public int counter;
	private boolean lock;
	private Random rand;
	private int width, height;
	private double[] uGridData, vGridData;
	private int[] uConcs, vConcs;
	private Chemical[][] uGrid, vGrid;

	public Grids(int width, int height) {
		rand = new Random(Driver.seed);
		
		this.lock = false;
		this.width = width;
		this.height = height;
		this.counter = 0;
		
		uGrid = new Chemical[width][height];
		vGrid = new Chemical[width][height];
		uGridData = new double[Driver.steps / 100];
		vGridData = new double[Driver.steps / 100];
		
		initializeGrids(uGrid, vGrid);
	}

	/**
	 * Function to reset the simulation.
	 */
	public void reset() {
		// reset the counter back to zero
		counter = 0;
		
		// reset the grids for the U and V chemicals along with the data used to track how the chemicals change
		uGrid = new Chemical[width][height];
		vGrid = new Chemical[width][height];
		uGridData = new double[Driver.steps / 100];
		vGridData = new double[Driver.steps / 100];
		
		// re-initialize the grids
		initializeGrids(uGrid, vGrid);
	}

	/**
	 * Function to retrieve either the current state of the U or V chemicals as a whole.
	 * 
	 * @param b		value for getting U or V chemicals
	 * @return		if b is true it returns the U chemicals else the V chemicals
	 */
	public Chemical[][] getGrid(boolean b) {
		return b ? uGrid : vGrid;
	}

	/**
	 * Function to retrieve the data of either the U or V chemicals as a whole.
	 * 
	 * @param b		value for getting U or V chemical data
	 * @return		if b is true it returns the U chemical data else the V chemical data
	 */
	public double[] getGridData(boolean b) {
		return b ? uGridData : vGridData;
	}

	/**
	 * Function to retrieve either the current distribution of either the U or V chemical concentrations.
	 * 
	 * @param b		value for getting distribution of U or V chemical concentrations
	 * @return		if b is true it returns the distribution of the U chemical concentrations
	 * 				else the V chemical concentrations
	 */
	public int[] getConcentrations(boolean b) {
		return b ? uConcs : vConcs;
	}

	/**
	 * Used in the ChemicalPanel class for painting and updating the screen
	 * @param b 	boolean value for the lock being set
	 */
	public void setLock(boolean b) {
		this.lock = b;
	}

	/**
	 * Returns whether the lock is set or not.
	 * 
	 * @return 		boolean value for the state of the lock
	 */
	public boolean getLock() {
		return lock;
	}

	public void initializeGrids(Chemical[][] grid1, Chemical[][] grid2) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// the distance from the x and y axes, treating the center of the grid as the origin
				double di = i - width * 0.5;
				double dj = j - height * 0.5;
				
				// the distance from the center of the grid
				double sq = Math.sqrt(di * di + dj * dj);
				
				// some functions used for changing initial V conditions; when activated, these give
				// aesthetically pleasing visualizations that are repetitive in nature 
				double cos1 = (Math.cos((width / 2 - sq) / 8) + 1) * 0.5;
				double cos2 = (Math.cos((width / 2 + di) / 8) + 1) * 0.5;
				double cos3 = (Math.cos((height / 2 + dj) / 8) + 1) * 0.5;
				
				// creates initial V conditions of concentric circles or a cos(x)*cos(y) function
				double co2 = (Driver.concentric) ? cos1 * cos1 : cos2 * cos3;

				// basically sets random points on the grid to have an initial V composition of 0 or 1
				double mid = (rand.nextInt(width * height / 8) - 1 <= 0) ? 1 : 0.0;

				grid1[i][j] = new Chemical(new URate(), 1.0); // U always begins with a 1.0 composition
				
				// sets V conditions to be either random or periodic in nature
				grid2[i][j] = new Chemical(new VRate(), Driver.random ? mid	: co2); 
																					
			}
		}
	}
	
	/**
	 * Function that goes through each point on the grid, applying the governing equations for the respective
	 * chemicals at each point.
	 */
	public void gridTransition() {
		// perform calculations for next step
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double uData = collectNeighborData(i, j, true);
				double vData = collectNeighborData(i, j, false);

				uGrid[i][j].transition(uData, vGrid[i][j].getComposition());
				vGrid[i][j].transition(vData, uGrid[i][j].getComposition());
			}
		}
	}

	/**
	 * Function to find the distribution of compositions for both U and V chemicals in the grid
	 */
	public void collectGridData() {
		uConcs = new int[101];
		vConcs = new int[101];
		double uSum = 0.0, vSum = 0.0;
		double area = width * height;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double uComp = uGrid[i][j].getComposition();
				double vComp = vGrid[i][j].getComposition();
				try {
					uConcs[(int) (uComp * 100)]++;
					vConcs[(int) (vComp * 100)]++;
				} catch (Exception e) {
				}
				uSum += uComp;
				vSum += vComp;
			}
		}
		uGridData[counter / 100] = uSum / ((double) area);
		vGridData[counter / 100] = vSum / ((double) area);
	}

	/**
	 * After the transition phase, all the chemicals at each point on the grid are updated to reflect how the
	 * transition changed the chemical compositions.
	 */
	public void update() {
		// update both grids
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				uGrid[i][j].update();
				vGrid[i][j].update();
			}
		}
		counter++;
	}

	/**
	 * Using wrap around conditions on the grid, this function takes the north, south, east, and west neighbors
	 * and sums up their compositions. If b is true, then we send back the sum of the neighboring U chemicals, else
	 * we send back the sum of the neighboring V chemicals.
	 * 
	 * @param x		The x location on the grid of the given chemical
	 * @param y		The y location on the grid of the given chemical
	 * @param b		Boolean value used to determine whether to send back the sum of the neighboring U or V chemicals
	 * @return		The sum of the neighboring chemicals
	 */
	public double collectNeighborData(int x, int y, boolean b) {
		double value;
		int n = (y - 1 < 0) ? height - 1 : y - 1;
		int e = (x + 1 >= width) ? 0 : x + 1;
		int s = (y + 1 >= height) ? 0 : y + 1;
		int w = (x - 1 < 0) ? width - 1 : x - 1;

		if (b) {
			value = uGrid[x][n].getComposition() + uGrid[e][y].getComposition()
					+ uGrid[x][s].getComposition()
					+ uGrid[w][y].getComposition();
		} else {
			value = vGrid[x][n].getComposition() + vGrid[e][y].getComposition()
					+ vGrid[x][s].getComposition()
					+ vGrid[w][y].getComposition();
		}

		return value;
	}
}
