package bc.diffusion.equations;

/**
 * This class provides an abstraction for how a chemical will change in time. The diffuse function is already defined
 * since it will be the same for either U or V chemicals. The interaction and transition functions are defined in the
 * URate and VRate classes which determine specifically how a U or V chemical will change at each time step.
 * 
 * @author Brennan Collins
 *
 */
public abstract class Rate {
	
	/**
	 * A function that determines how the chemical will diffuse with surrounding chemicals of the same type.
	 * 	
	 * @param neighbors		the combined rates of the neighboring chemicals of the same type
	 * @param cell			the current composition of the chemical at the given location
	 * @return				a gradient function that evens out the compositions of neighboring chemicals
	 */
	public double diffuse(double neighbors, double cell) {
		return neighbors - 4 * cell;
	}
	
	/**
	 * This function determines how chemicals of different types (U or V) will interact at a given location.
	 * 
	 * @param c1			The chemical this rate is representing
	 * @param c2			The opposing chemical at the same location
	 * @return				a function representing how the two chemicals interact at the given time step
	 */
	public abstract double interaction(double c1, double c2);
	
	/**
	 * This function transitions the chemical composition given the diffusion and interaction rates at the current
	 * time step.
	 * 
	 * @param chem			The current composition of the chemical at a given point on the grid
	 * @param diffRate		The rate at which the chemical has diffused with its surroundings at the current
	 * 						time step.
	 * @param intRate		The rate at which the chemical changes due to its interaction with the opposing
	 * 						chemical at the same point on the grid.
	 * @return				A function representing the entirety of how the chemical has changed at the given 
	 * 						time step.
	 */
	public abstract double transition(double chem, double diffRate, double intRate);
}
