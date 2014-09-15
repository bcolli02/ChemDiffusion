package bc.diffusion.main;

import java.awt.Color;
import java.awt.Graphics;

public class UPlotter extends Plotter {

	private static final long serialVersionUID = 1L;

	public UPlotter(Grids grids, int width, int height) {
		super(grids, width, height);
	}

	@Override
	public void refreshGraph(Graphics g, int t) {
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		int start = (int)(width - lift);
		int count = t / 100, length = width / 100;
		double[] gridData = grids.getGridData(true);
		for (int i = 0; i < count; i++) {
			double h1 = lift - (gridData[i] * maxHeight), h2 = lift
					- (gridData[i + 1] * maxHeight);
			g.setColor(Color.blue);
			g.drawLine(i * length + start, (int) h1, (i + 1) * length + start, (int) h2);
		}
	}
}
