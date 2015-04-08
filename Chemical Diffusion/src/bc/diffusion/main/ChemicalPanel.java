package bc.diffusion.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import bc.diffusion.chemicals.Chemical;

/**
 * This is a class that does the visualization for how the chemicals in the grid change in time.
 * 
 * @author Brennan Collins
 *
 */
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
		
		// only paint if the simulation has not completed and is not paused
		if (grids.counter <= Driver.steps && !Driver.paused) {
			
			// to improve performance, we only paint every number of time steps based on the size of the grid
			for (int i = 0; i < 20 / Driver.scale; i++) {
				grids.gridTransition(); // transition function for the simulation at the given time step
				grids.update();	// update function that changes chemical compositions to reflect the transition phase
			}
		}
		
		grids.setLock(true);
		refreshPanel(g);
	}

	public void refreshPanel(Graphics g) {
		// variables that represent the compositions of all the U and V chemicals at the current time step
		Chemical[][] uGrid = grids.getGrid(true);
		Chemical[][] vGrid = grids.getGrid(false);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// a color variable for painting to the screen
				Color color;
				// U and V compositions at a given (x, y) location on the grid
				double ug = Math.abs(uGrid[i][j].getComposition());
				double vg = Math.abs(vGrid[i][j].getComposition());
				
				// variables used to determine how to paint
				int inCo = (int) (1000 * (ug * vg)) % 256;
				int uinCo = (int) (255 * ug) % 256;
				int vinCo = (int) (255 * vg) % 256;
				
				// when toggled, only U or V compositions will be displayed to the screen
				if (Driver.toggle == 0)
					color = new Color(vinCo, 255 - inCo, uinCo);
				else
					color = (Driver.toggle == 1) ? new Color(255 - uinCo,
							255 - uinCo, 255) : new Color(255, 255 - vinCo,
							255 - vinCo);
					
				// finally set the color and paint the representation of the chemicals at the given location	
				g.setColor(color);
				g.drawRect(i * Driver.scale, j * Driver.scale, (i + 1)
						* Driver.scale, (j + 1) * Driver.scale);
			}
		}
	}
}
