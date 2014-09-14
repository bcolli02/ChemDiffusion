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
		int count = t / 100;
		double[] gridData = grids.getGridData(true);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < count; i++) {
			double h1 = height - (gridData[i] * 0.5 * height) + 50, h2 = height
					- (gridData[i + 1] * 0.5 * height) + 50;
			g.setColor(Color.black);
			g.drawLine(i * count + 40, (int) h1, (i + 1) * count + 40, (int) h2);
			if (t % 100 == 0)
				System.out.println("Collective u data: " + h2);
		}
	}
}
