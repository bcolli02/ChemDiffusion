package bc.diffusion.main;

import java.util.Random;

import bc.diffusion.chemicals.Chemical;
import bc.diffusion.equations.URate;
import bc.diffusion.equations.VRate;

public class Grids {

	private Random rand;
	private boolean lock;
	private int width, height;
	private Chemical[][] uGrid, vGrid;

	public Grids(int width, int height) {
		rand = new Random(Driver.seed);
		this.lock = false;
		this.width = width;
		this.height = height;
		uGrid = new Chemical[width][height];
		vGrid = new Chemical[width][height];
		initializeGrids(uGrid, vGrid);
	}

	public Chemical[][] getGrid(boolean b) {
		return b ? uGrid : vGrid;
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
				double r1 = rand.nextInt(10000) / 10000, 
						r2 = rand.nextInt(10000) / 10000;
				grid1[i][j] = new Chemical(new URate(), r1);
				grid2[i][j] = new Chemical(new VRate(), r2);
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

	public void update() {
		// update both grids
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				uGrid[i][j].update();
				vGrid[i][j].update();
			}
		}
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