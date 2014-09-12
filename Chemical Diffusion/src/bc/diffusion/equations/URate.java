package bc.diffusion.equations;

import bc.diffusion.main.Driver;

public class URate extends Rate {

	@Override
	public double interaction(double u, double v) {
		double vs = v * v;
		double rate = (Driver.f * (1 - u)) - (u * vs);
		return rate;
	}

	@Override
	public double transition(double chem, double diffRate, double intRate) {
		double rate = chem + (Driver.ru * diffRate) + intRate;
		return rate;
	}

}
