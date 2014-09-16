package bc.diffusion.main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class Plotter extends JPanel {

	private static final long serialVersionUID = 1L;
	public Grids grids;
	public int width, height, timeStep, length;

	public Plotter(Grids grids, int width, int height) {
		this.grids = grids;
		this.width = width;
		this.height = height;
		this.length = width / 100;
		timeStep = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		layGrid(g);
		if (grids.getLock() && timeStep % 100 == 0 && timeStep < 10000)
			grids.collectGridData(timeStep);
		if (timeStep < 10000)
			refreshGraph(g, timeStep);
		else {
			refreshGraph(g, 9999);
			System.out.println("Max Steps reached.");
		}
		timeStep++;
	}

	public void layGrid(Graphics g) {
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		// Filling drawing area
		g.setColor(new Color(241, 240, 166));
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
	}

	public abstract void refreshGraph(Graphics g, int t);
}
