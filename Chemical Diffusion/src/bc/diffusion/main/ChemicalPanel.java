package bc.diffusion.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class ChemicalPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public int width, height, counter = 0;
	public Grids grids;

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
		if(!grids.getLock()) {
			grids.gridTransition();
			grids.update();
			grids.setLock(true);
		} else {
			grids.setLock(false);
		}
			
		refreshPanel(g);
		if(counter >= Driver.steps)
			System.out.println("Max steps reached");
		counter++;
	}

	public abstract void refreshPanel(Graphics g);
}
