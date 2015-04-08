package bc.diffusion.main;

import javax.swing.JOptionPane;

public class Driver {

	// below are global variables used in the simulation that affect output results
	public static boolean paused = false, random = false, concentric = false;
	public static int seed = 0, toggle = 0, steps = 15000, scale = 2;
	public static double ru = 0.16, rv = 0.08, f = 0.02, k = 0.055;

	public static void main(String[] args) {
		String input;
		try {
			// gives a dialog to determine how big the grid should be in the simulation: larger numbers
			// give a smaller working space, but increase time performance, and smaller numbers give a
			// larger working space, but decreases time performance
			input = JOptionPane.showInputDialog("Enter in a value for scale:");
			Driver.scale = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("Using default values...");
		}

		// create the GUI for the simulation
		final DiffusionGUI diffGui = new DiffusionGUI();

		// start up the thread for the simulation
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				diffGui.createAndShowGUI();
			}
		});
	}
}
