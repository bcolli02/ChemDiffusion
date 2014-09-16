package bc.diffusion.main;

import javax.swing.JOptionPane;

public class Driver {

	public static int seed = 0, steps = 10000, scale = 2;
	public static double ru = 0.16, rv = 0.08, f = 0.02, k = 0.055;

	public static void main(String[] args) {
		String input;
		try {
			input = JOptionPane
					.showInputDialog("Enter in values for scale, ru, rv, f, and k separated by spaces:");
			String vals[] = input.split(" ");
			Driver.scale = Integer.parseInt(vals[0]);
			Driver.ru = Double.parseDouble(vals[1]);
			Driver.rv = Double.parseDouble(vals[2]);
			Driver.f = Double.parseDouble(vals[3]);
			Driver.k = Double.parseDouble(vals[4]);
		} catch (Exception e) {
			System.err.println("Using default values...");
		}

		final DiffusionGUI diffGui = new DiffusionGUI();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				diffGui.createAndShowGUI();
			}
		});
	}
}
