package bc.diffusion.main;

import java.util.Random;

import bc.diffusion.chemicals.Chemical;
import bc.diffusion.equations.URate;
import bc.diffusion.equations.VRate;

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

	public void reset() {
		counter = 0;
		uGrid = new Chemical[width][height];
		vGrid = new Chemical[width][height];
		uGridData = new double[Driver.steps / 100];
		vGridData = new double[Driver.steps / 100];
		initializeGrids(uGrid, vGrid);
	}

	public Chemical[][] getGrid(boolean b) {
		return b ? uGrid : vGrid;
	}

	public double[] getGridData(boolean b) {
		return b ? uGridData : vGridData;
	}

	public int[] getConcentrations(boolean b) {
		return b ? uConcs : vConcs;
	}

	public void setLock(boolean b) {
		this.lock = b;
	}

	public boolean getLock() {
		return lock;
	}

	public void initializeGrids(Chemical[][] grid1, Chemical[][] grid2) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double di = i - width * 0.5;
				double dj = j - height * 0.5;
				double sq = Math.sqrt(di * di + dj * dj);
				double cos1 = (Math.cos((width / 2 - sq) / 8) + 1) * 0.5;
				double cos2 = (Math.cos((width / 2 + di) / 8) + 1) * 0.5;
				double cos3 = (Math.cos((height / 2 + dj) / 8) + 1) * 0.5;
				double co2 = (Driver.concentric) ? cos1 * cos1 : cos2 * cos3;

				double mid = (rand.nextInt(width * height / 8) - 1 <= 0) ? 1.0
						: 0.0;

				grid1[i][j] = new Chemical(new URate(), 1.0);
				grid2[i][j] = new Chemical(new VRate(), Driver.random ? mid
						: co2);
			}
		}
	}

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
