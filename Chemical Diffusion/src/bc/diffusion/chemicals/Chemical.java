package bc.diffusion.chemicals;

import bc.diffusion.equations.Rate;

public class Chemical {

	private double composition, dComposition = 0.0;
	private Rate rate;
	
	public Chemical(Rate rate, double initComp) {
		this.rate = rate;
		this.composition = initComp;
	}
	
	public double getComposition() {
		return this.composition;
	}
	
	public void transition(double neighbors, double oChem) {
		double diffRate = rate.diffuse(neighbors, this.composition);
		double intRate = rate.interaction(this.composition, oChem);
		dComposition = rate.transition(this.composition, diffRate, intRate);
	}
	
	public void update() {
		this.composition = this.dComposition;
	}
}
