package bc.diffusion.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Plotter extends JPanel {

	private static final long serialVersionUID = 1L;
	private Grids grids;
	private boolean complete = false;
	private int width, height, timeStep;

	public Plotter(Grids grids, int width, int height) {
		this.grids = grids;
		this.width = width;
		this.height = height;
		timeStep = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		layGrid(g);
		if (grids.getLock() && timeStep % 100 == 0 && timeStep < 10000)
			grids.collectGridData(timeStep);
		if (timeStep < Driver.steps)
			refreshGraph(g, timeStep);
		else if (timeStep >= Driver.steps && !complete) {
			complete = true;
			refreshGraph(g, Driver.steps - 1);
			System.out.println("Max Steps reached.");
		} else
			refreshGraph(g, Driver.steps - 1);
		timeStep++;
	}

	public void layGrid(Graphics g) {
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		// Filling drawing area
		g.setColor(new Color(241, 240, 250));
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawLine((int) (width - lift - 2), (int) lift + 2, (int) (width
				- lift - 2), 0);
		g.drawString("Y", (int) (width - lift - 20), height / 2);
		g.drawLine((int) (width - lift - 2), (int) lift + 2, width,
				(int) lift + 2);
		g.drawString("X", width / 2, (int) (lift + 20));
		g.drawLine((int) (width - lift - 2), (int) (lift - maxHeight - 2),
				width - 2, (int) (lift - maxHeight - 2));
		g.drawString("1.0", (int) (width - lift - 25), (int) (lift - maxHeight));
		g.drawString("0.0", (int) (width - lift - 25), (int) lift);
		
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.blue);
		g.drawString("U", (int) lift + 15, (int) lift + 25);
		g.setColor(Color.red);
		g.drawString("V", (int) lift + 35, (int) lift + 25);
	}

	public void refreshGraph(Graphics g, int t) {
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		int start = (int) (width - lift);
		int count = t / 100, length = width / 100;
		double[] uGridData = grids.getGridData(true);
		double[] vGridData = grids.getGridData(false);
		for (int i = 0; i < count; i++) {
			double h1 = lift - (uGridData[i] * maxHeight), h2 = lift
					- (uGridData[i + 1] * maxHeight), h3 = lift
					- (vGridData[i] * maxHeight), h4 = lift
					- (vGridData[i + 1] * maxHeight);
			g.setColor(Color.blue);
			g.drawLine(i * length + start, (int) h1, (i + 1) * length + start,
					(int) h2);
			g.setColor(Color.red);
			g.drawLine(i * length + start, (int) h3, (i + 1) * length + start,
					(int) h4);
		}
	}
}
