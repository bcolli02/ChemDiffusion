package bc.diffusion.main;

import javax.swing.JOptionPane;

public class Driver {

	public static boolean paused = false, random = false;
	public static int seed = 0, toggle = 0, steps = 15000, scale = 2;
	public static double ru = 0.16, rv = 0.08, f = 0.02, k = 0.055;

	public static void main(String[] args) {
		String input;
		try {
			input = JOptionPane.showInputDialog("Enter in a value for scale:");
			Driver.scale = Integer.parseInt(input);
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
