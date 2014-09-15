package bc.diffusion.main;

import java.util.Scanner;

public class Driver {

	public static int seed = 0, steps = 10000;
	public static double ru = 0.19, rv = 0.05, f = 0.06, k = 0.062;

	public static void main(String[] args) {
		final DiffusionGUI diffGui = new DiffusionGUI();
		
		if (args[0] == "-i") {
//			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter a seed value:");
			seed = scanner.nextInt();
			System.out.println("Please enter a value for ru:");
			ru = scanner.nextDouble();
			System.out.println("Please enter a value for rv:");
			rv = scanner.nextDouble();
			System.out.println("Please enter a value for f:");
			f = scanner.nextDouble();
			System.out.println("Please enter a value for k:");
			k = scanner.nextDouble();
		} 
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				diffGui.createAndShowGUI();
			}
		});
	}

}
