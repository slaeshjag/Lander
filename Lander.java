public class Lander {
	float				velocity;
	float				altitude;
	float				fuel;
	boolean				last_engine_status;
	

	static final float		gravity = -0.97f;

	/* Egen observation: sätter man dessa till 1 så tar bränslet i princip aldrig slut. Men det verkar som att det ska göra det från uppgiften? */
	static final float		engine_accel = 5.0f;
	static final float		engine_fuel_usage = 5.0f;

	static final float		crash_velocity = -10.0f;

	public static enum landerResult {
		SIMULATION_SUCCESS,
		SIMULATION_FUEL_FAIL,
		SIMULATION_CRASH_FAIL
	}

	/* Initierar månlandaren med ett förbestämt state */
	public Lander(float velocity, float altitude, float fuel) {
		this.velocity = velocity;
		this.altitude = altitude;
		this.fuel = fuel;

		return;
	}

	
	/* True if simulation should continue. Applies physics and engine acceleration */
	public boolean loop(boolean engine_on) {
		altitude += velocity;
		velocity += gravity;
		last_engine_status = engine_on;
		if (engine_on == true) {
			velocity += engine_accel;
			fuel -= engine_fuel_usage;
		}
		
		return (fuel > 0 && altitude > 0) ? true : false;
	}

	/* Determines if the simulation was successful or not */
	public landerResult landerOutcome() {
		if (fuel <= 0)
			return landerResult.SIMULATION_FUEL_FAIL;
		if (velocity <= crash_velocity)
			return landerResult.SIMULATION_CRASH_FAIL;
		return landerResult.SIMULATION_SUCCESS;
	}
	
	
	public float getVelocity() {
		return velocity;
	}


	public float getAltitude() {
		return altitude;
	}


	public float getFuel() {
		return fuel;
	}

	
	public boolean getEngineStatus() {
		return last_engine_status;
	}
}

