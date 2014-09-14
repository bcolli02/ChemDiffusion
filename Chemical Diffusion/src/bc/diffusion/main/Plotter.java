package bc.diffusion.main;

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
		if (grids.getLock())
			grids.collectGridData(timeStep);
		refreshGraph(g, timeStep);
		timeStep++;
	}

	public abstract void refreshGraph(Graphics g, int t);
}
