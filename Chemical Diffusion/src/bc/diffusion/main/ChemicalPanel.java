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
		if (grids.counter <= Driver.steps && !Driver.paused) {
			for (int i = 0; i < 20 / Driver.scale; i++) {
				grids.gridTransition();
				grids.update();
			}
		}
		grids.setLock(true);
		refreshPanel(g);
	}

	public void refreshPanel(Graphics g) {
		Chemical[][] uGrid = grids.getGrid(true);
		Chemical[][] vGrid = grids.getGrid(false);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Color color;
				double ug = Math.abs(uGrid[i][j].getComposition());
				double vg = Math.abs(vGrid[i][j].getComposition());
				int inCo = (int) (1000 * (ug * vg)) % 256;
				int uinCo = (int) (255 * ug) % 256;
				int vinCo = (int) (255 * vg) % 256;
				if (Driver.toggle == 0)
					color = new Color(vinCo, 255 - inCo, uinCo);
				else
					color = (Driver.toggle == 1) ? new Color(255 - uinCo,
							255 - uinCo, 255) : new Color(255, 255 - vinCo,
							255 - vinCo);
				g.setColor(color);
				g.drawRect(i * Driver.scale, j * Driver.scale, (i + 1)
						* Driver.scale, (j + 1) * Driver.scale);
			}
		}
	}
}
