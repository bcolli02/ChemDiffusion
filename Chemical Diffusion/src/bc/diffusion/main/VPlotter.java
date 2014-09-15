package bc.diffusion.main;

import java.awt.Color;
import java.awt.Graphics;

public class VPlotter extends Plotter {

	private static final long serialVersionUID = 1L;

	public VPlotter(Grids grids, int width, int height) {
		super(grids, width, height);
	}

	@Override
	public void refreshGraph(Graphics g, int t) {
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		int count = t / 100, length = width / 100;
		double[] gridData = grids.getGridData(false);
		for (int i = 0; i < count; i++) {
			double h1 = lift - (gridData[i] * maxHeight), h2 = lift
					- (gridData[i + 1] * maxHeight);
			g.setColor(Color.blue);
			g.drawLine(i * length + 80, (int) h1, (i + 1) * length + 80, (int) h2);
		}
	}
}
