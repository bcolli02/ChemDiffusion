package bc.diffusion.main;

import java.awt.Color;
import java.awt.Graphics;

import bc.diffusion.chemicals.Chemical;

public class UPanel extends ChemicalPanel {

	private static final long serialVersionUID = 1L;

	public UPanel(Grids grids, int width, int height) {
		super(grids, width, height);
	}

	@Override
	public void refreshPanel(Graphics g) {
		Chemical[][] uGrid = grids.getGrid(true);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				double d = Math.abs(uGrid[i][j].getComposition());
//				if (i == width / 2 && j == height / 2)
//					System.out.println("U[" + i + "]" + "[" + j
//							+ "] at timestep " + counter + " is: " + d);
				Color color = new Color(30, ((int) (255 - d * 135)), 125);
				g.setColor(color);
				g.drawRect(i, j, i, j);
			}
		}
	}
}
