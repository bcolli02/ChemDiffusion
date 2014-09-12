package bc.diffusion.equations;

public abstract class Rate {
	
	public double diffuse(double neighbors, double cell) {
		return neighbors - 4 * cell;
	}
	
	public abstract double interaction(double u, double v);
	
	public abstract double transition(double chem, double diffRate, double intRate);
}
