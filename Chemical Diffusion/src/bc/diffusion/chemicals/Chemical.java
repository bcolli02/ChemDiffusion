package bc.diffusion.chemicals;

import bc.diffusion.equations.Rate;

/**
 * This classes represents an instance of a chemical in the simulation. In the simulation, the chemicals are 
 * laid out on a grid and there is both a U and V chemical at each point on the grid, so an instance of the 
 * Chemical class will represent either of these chemicals at a given point on the grid.
 * 
 * @author Brennan Collins
 *
 */
public class Chemical {
		
	// variables for current chemical composition and the change in composition at current time step 
	private double composition, dComposition = 0.0; 
	private Rate rate; // this determines the underlying equations for how the chemical changes in time
	
	/**
	 *	Constructor for the Chemical class. It takes a rate which determines how the chemical will change
	 *	at each time step along with an initial composition of the chemical. 
	 *
	 * @param rate			Variable for determining how chemical will change in time
	 * @param initComp		Initial composition of chemical: anywhere from 0.0 to 1.0
	 */
	public Chemical(Rate rate, double initComp) {
		this.rate = rate;
		this.composition = initComp;
	}
	
	/**
	 * Simply gives you the current composition of the chemical.
	 * 
	 * @return the current composition of the chemical at the given location.
	 */
	public double getComposition() {
		return this.composition;
	}
	
	/**
	 *	This is the transition function for the chemical at each time step. It uses the rate class for to
	 *	determine how it diffuses and interacts with its neighbors (only NSEW). It uses the dComposition variable
	 *	as a temporary place holder for how the chemical has changed during the current time step.
	 * 	
	 * @param neighbors		The neighboring chemicals that are of the same type as this chemical (U or V)
	 * @param oChem			The composition of the opposing chemical at this chemical's location (U for V and V for U)
	 */
	public void transition(double neighbors, double oChem) {
		double diffRate = rate.diffuse(neighbors, this.composition);
		double intRate = rate.interaction(this.composition, oChem);
		dComposition = rate.transition(this.composition, diffRate, intRate);
	}
	
	/**
	 * Simply updates the current composition with how the chemical has changed at this time step.
	 */
	public void update() {
		this.composition = this.dComposition;
	}
}
