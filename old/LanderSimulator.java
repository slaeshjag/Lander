///////////////////////////////////////////////////////////////////////////
// LanderSimulator.java                                                  //
// Copyleft Steven Arnow <s@rdw.se> 2012, some rights reserved           //
///////////////////////////////////////////////////////////////////////////


public class LanderSimulator {
	protected float		fuel_max;
	protected float		height_max;
	protected float		velocity_max;
	protected float		gravity;

	protected int		dimension_resolution;

	protected Ai		ai;
	
	
	public int fuelIndex(float fuel) {
		int fuel_index;

		// Calculate the fuel index in the learning matrix for the fuel value //
		return (int) ((fuel / fuel_max) * dimension_resolution);
	}


	public int heightIndex(float height) {
		int height_index;

		// Calculate the height index in the learning matrix for the height //
		return (int) ((height / height_max) * dimension_resolution);
	}


	public int velocityIndex(float velocity) {
		int velocity_index;

		// Calculate the velocity index in the learning matrix for the velocity //
		return (int) ((velocity / velocity_max) * dimension_resolution);
	}
	


	public LanderSimulator(float fuel_max, float height_max, float velocity_max, float gravity, int dimensional_resolution) {
		ai = new Ai(dimensional_resolution);

		dimension_resolution = dimensional_resolution;
		this.fuel_max = fuel_max;
		this.height_max = height_max;
		this.velocity_max = velocity_max;
		this.gravity = gravity;

	}
}
