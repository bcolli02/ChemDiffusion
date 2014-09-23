package bc.diffusion.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

public class Plotter extends JPanel {

	private static final long serialVersionUID = 1L;
	DecimalFormat df;
	private Grids grids;
	private boolean complete = false;
	private int width, height;

	public Plotter(Grids grids, int width, int height) {
		df = new DecimalFormat("#0.####");
		this.grids = grids;
		this.width = width;
		this.height = height;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		layGrid(g);
		if (grids.getLock() && grids.counter % 100 == 0
				&& grids.counter < Driver.steps)
			grids.collectGridData();
		if (grids.counter < Driver.steps)
			refreshGraph(g, grids.counter);
		else if (grids.counter >= Driver.steps && !complete) {
			complete = true;
			refreshGraph(g, Driver.steps - 1);
			System.out.println("Max Steps reached.");
		} else
			refreshGraph(g, Driver.steps - 1);
	}

	public void layGrid(Graphics g) {
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		// Filling drawing area
		g.setColor(new Color(241, 240, 250));
		g.fillRect(0, 0, 2 * width, 2 * height);
		g.setColor(Color.black);
		g.drawLine((int) (width - lift - 2), (int) lift + 2, (int) (width
				- lift - 2), 0);
		g.drawString("Y", (int) (width - lift - 20), height / 2);
		g.drawLine((int) (width - lift - 2), (int) lift + 2, 2 * width,
				(int) lift + 2);
		g.drawString("X", width / 2, (int) (lift + 20));
		g.drawLine((int) (width - lift - 2), (int) (lift - maxHeight - 2),
				2 * width - 2, (int) (lift - maxHeight - 2));
		g.drawString("1.0", (int) (width - lift - 25), (int) (lift - maxHeight));
		g.drawString("0.0", (int) (width - lift - 25), (int) lift);

		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.setColor(Color.blue);
		g.drawString("U", (int) lift + 185, (int) lift + 25);
		g.setColor(Color.red);
		g.drawString("V", (int) lift + 185, (int) lift + 45);
	}

	public void refreshGraph(Graphics g, int t) {
		double uC = 0.0, vC = 0.0;
		double lift = 0.83333 * height, maxHeight = 0.66667 * height;
		int start = (int) (width - lift);
		int count = t / 100, length = width / 100;
		double[] uGridData = grids.getGridData(true);
		double[] vGridData = grids.getGridData(false);
		int[] uConcs = grids.getConcentrations(true);
		int[] vConcs = grids.getConcentrations(false);
		for (int i = 0; i < count; i++) {
			uC = uGridData[i];
			vC = vGridData[i];
			double h1 = lift - (uGridData[i] * maxHeight), h2 = lift
					- (uGridData[i + 1] * maxHeight), h3 = lift
					- (vGridData[i] * maxHeight), h4 = lift
					- (vGridData[i + 1] * maxHeight);

			if (Driver.toggle == 0) {
				g.setColor(Color.DARK_GRAY);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Net Chemical Concentrations", width / 2 + 50,
						(int) (lift - maxHeight - 25));
				g.setColor(Color.blue);
				g.drawLine(i * length + start, (int) h1, (i + 1) * length
						+ start - 1, (int) h2);
				g.setColor(Color.red);
				g.drawLine(i * length + start, (int) h3, (i + 1) * length
						+ start - 1, (int) h4);
			}
		}

		if (Driver.toggle == 1) {
			for (int i = 0; i < 100; i++) {
				double a = (double) (height * width);
				double u = (double) uConcs[i] / a;
				double h1 = lift - 4 * u * maxHeight;

				g.setColor(Color.DARK_GRAY);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Chemical Distribution of U", width / 2 + 50,
						(int) (lift - maxHeight - 25));

				g.setColor(Color.blue);
				g.drawRect(i * (length + 3) + start, (int) h1, length + 3,
						(int) (lift - (h1 - 0.5)));
			}
		}
		if (Driver.toggle == 2) {
			for (int i = 0; i < 100; i++) {
				double a = (double) (height * width);
				double v = (double) vConcs[i] / a;
				double h1 = lift - 4 * v * maxHeight;

				g.setColor(Color.DARK_GRAY);
				g.setFont(new Font("Arial", Font.BOLD, 18));
				g.drawString("Chemical Distribution of V", width / 2 + 50,
						(int) (lift - maxHeight - 25));

				g.setColor(Color.red);
				g.drawRect(i * (length + 3) + start, (int) h1, length + 3,
						(int) (lift - (h1 - 0.5)));
			}
		}

		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.setColor(Color.black);
		g.drawString(df.format(uC), (int) (lift + 215), (int) (lift + 25));
		g.drawString(df.format(vC), (int) (lift + 215), (int) (lift + 45));
	}
}
