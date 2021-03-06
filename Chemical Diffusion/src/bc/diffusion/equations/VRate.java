package bc.diffusion.equations;

import bc.diffusion.main.Driver;

/**
 * This class represents the rate at which any V chemical will change at each time step.
 * 
 * @author Brennan Collins
 *
 */
public class VRate extends Rate {
	
	@Override
	public double interaction(double v, double u) {
		double vs = v * v;
		double rate = (u * vs) - ((Driver.f + Driver.k) * v);
		return rate;
	}

	@Override
	public double transition(double chem, double diffRate, double intRate) {
		double rate = chem + (Driver.rv * diffRate) + intRate;
		return rate;
	}
}
