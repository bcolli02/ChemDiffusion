package bc.diffusion.main;

import java.awt.Color;
import java.awt.Graphics;

import bc.diffusion.chemicals.Chemical;

public class VPanel extends ChemicalPanel {

	private static final long serialVersionUID = 1L;

	public VPanel(Grids grids, int width, int height) {
		super(grids, width, height);
	}

	@Override
	public void refreshPanel(Graphics g) {
		Chemical[][] vGrid = grids.getGrid(false);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double d = Math.abs(vGrid[i][j].getComposition());
//				if (i == width / 2 && j == height / 2)
//					System.out.println("V[" + i + "]" + "[" + j
//							+ "] at timestep " + counter + " is: " + d);
				Color color = new Color(((int)(200 - d * 50)), ((int)(220 - d * 100)), 135);
				g.setColor(color);
				g.drawRect(i, j, i, j);
			}
		}
	}
}
