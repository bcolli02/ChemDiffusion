package bc.diffusion.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import bc.diffusion.chemicals.Chemical;

public class ChemicalPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width, height;
	private Grids grids;

	public ChemicalPanel(Grids grids, int width, int height) {
		this.grids = grids;
		this.width = width;
		this.height = height;

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(width, height));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (grids.counter <= Driver.steps) {
			for (int i = 0; i < 20 / Driver.scale; i++) {
				grids.gridTransition();
				grids.update();
			}
			grids.setLock(true);
		}
		refreshPanel(g);
	}

	public void refreshPanel(Graphics g) {
		Chemical[][] uGrid = grids.getGrid(true);
		Chemical[][] vGrid = grids.getGrid(false);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double ug = Math.abs(uGrid[i][j].getComposition());
				double vg = Math.abs(vGrid[i][j].getComposition());
				int inCo = (int) (1000 * ug * vg);
				Color color = new Color(255 - inCo, 255 - inCo / 2,
						255 - inCo / 4);
				g.setColor(color);
				g.drawRect(i * Driver.scale, j * Driver.scale, (i + 1)
						* Driver.scale, (j + 1) * Driver.scale);
			}
		}
	}
}
