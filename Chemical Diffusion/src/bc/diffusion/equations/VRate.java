package bc.diffusion.equations;

import bc.diffusion.main.Driver;

public class VRate extends Rate {
	
	@Override
	public double interaction(double u, double v) {
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
