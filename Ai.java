import java.util.LinkedList;
import java.util.Random;


public class Ai {
	public static enum DecisionOutcome {
		SUCCESS,
		CRASH,
		FUEL
	}


	/* Order of elements is fuel, velocity and altitude */
	LearningMatrix				learning_matrix[][][];

	LinkedList<SimulationRecording>		simulation_recording;
	Lander					lander;
	Random					rand;
	DecisionOutcome				last_attempt;

	final int				max_fuel = 80;

	final int				max_spawn_fuel = 125;
	final int				max_spawn_altitude = 200;
	final int				max_spawn_velocity = 5;
	final int				min_spawn_fuel = 75;
	final int				min_spawn_altitude = 100;
	final int				min_spawn_velocity = -5;

	
	/* Översätter månlandarens bränslenivå till ett index i arrayen som innehåller inlärningsdatan */
	public int matrixTranslateFuel() {
		float fuel = lander.getFuel();

		if (fuel < 0)
			fuel = 0.0f;

		/* This is completely linear. Easy to translate */
		return ((int) fuel / (max_fuel / 8) > 7) ? 7 : (int) fuel / (max_fuel / 8);
	}


	/* Översätter månlandarens position till ett index i arrayen som innehåller inlärningsdatan */
	public int matrixTranslateAltitude() {
		float altitude = lander.getAltitude();

		/* Jommpalösning deluxe... */
		if (altitude < 5)
			return 0;
		if (altitude < 10)
			return 1;
		if (altitude < 15)
			return 2;
		if (altitude < 20)
			return 3;
		if (altitude < 25)
			return 4;
		if (altitude < 50)
			return 5;
		if (altitude < 100)
			return 6;
		return 7;
	}


	/* Översätter månlandarens hastighet till ett index i arrayen som innehåller inlärningsdatan */
	public int matrixTranslateVelocity() {
		float velocity = lander.getVelocity();

		/* Ännu en Jommpalösning... */
		if (velocity < -30)
			return 0;
		if (velocity < -20)
			return 1;
		if (velocity < -10)
			return 2;
		if (velocity < -1)
			return 3;
		if (velocity < 1)
			return 4;
		if (velocity < 10)
			return 5;
		if (velocity < 20)
			return 6;
		return 7;
	}


	/* Slår upp månlandarens parametrar i inlärningsmatrisen och använder den för att avgöra om motorn ska vara på eller inte. True är motor på */
	public boolean decideAction() {
		int fuel, velocity, altitude;

		fuel = matrixTranslateFuel();
		velocity = matrixTranslateVelocity();
		altitude = matrixTranslateAltitude();

		/* Är villkoret sant så är det lika mellan på och av. Då väljs motor på eller av slumpvis. */
		if (learning_matrix[fuel][velocity][altitude].engine_on == learning_matrix[fuel][velocity][altitude].engine_off)
			return (rand.nextInt(1) > 0) ? true : false;
		
		/* Returnerar motor på eller av enligt inlärningsmatris */
		return (learning_matrix[fuel][velocity][altitude].engine_off > learning_matrix[fuel][velocity][altitude].engine_on) ? false : true;
	}


	/* Sparar ett beslut så att inlärningsmatrisen kan fyllas i efter landningen */
	public void pushLog(boolean motor_on) {
		SimulationRecording sim_rec = new SimulationRecording();

		sim_rec.fuel = matrixTranslateFuel();
		sim_rec.velocity = matrixTranslateVelocity();
		sim_rec.altitude = matrixTranslateAltitude();
		sim_rec.motor_on = motor_on;
		simulation_recording.add(sim_rec);
	}


	/* Spelar tillbaka besluten som gjordes under landningen och fyller i inlärningsmatrisen utefter om landningen var lyckad eller inte */
	public void popLogs() {
		int delta;
		switch (lander.landerOutcome()) {
			case SIMULATION_SUCCESS:
				last_attempt = DecisionOutcome.SUCCESS;
				delta = 2;
				break;
			case SIMULATION_CRASH_FAIL:
				last_attempt = DecisionOutcome.CRASH;
				delta = -1;
				break;
			case SIMULATION_FUEL_FAIL:
				last_attempt = DecisionOutcome.FUEL;
				delta = -1;
				break;
			default:
				delta = 0;
		}

		for (SimulationRecording sim_rec : simulation_recording) {
			if (sim_rec.motor_on)
				learning_matrix[sim_rec.fuel][sim_rec.velocity][sim_rec.altitude].engine_on += delta;
			else
				learning_matrix[sim_rec.fuel][sim_rec.velocity][sim_rec.altitude].engine_off += delta;
		}

		simulation_recording = null;
	}


	public DecisionOutcome getSuccess() {
		return last_attempt;
	}


	public Ai() {
		int i, j, k;
		rand = new Random();
		last_attempt = DecisionOutcome.SUCCESS;
		learning_matrix = new LearningMatrix[8][8][8];

		for (i = 0; i < 8; i++)
			for (j = 0; j < 8; j++)
				for (k = 0; k < 8; k++)
					learning_matrix[i][j][k] = new LearningMatrix();

		return;
	}
	
	/* Spawns a new lander with random parameters */
	public Lander newLander() {
		int altitude, velocity, fuel;
		Lander lander;

		/* We need a new linked list for storing what actions we take during simulation */
		simulation_recording = new LinkedList<SimulationRecording>();
		
		/* Slumpar fram parametrar för månlandarens startläge */
		fuel = rand.nextInt(max_spawn_fuel - min_spawn_fuel) + min_spawn_fuel;
		velocity = rand.nextInt(max_spawn_velocity - min_spawn_velocity) + min_spawn_velocity;
		altitude = rand.nextInt(max_spawn_altitude - min_spawn_altitude) + min_spawn_altitude;

		/* Skapar en månlandare med dessa parametrar */
		lander = new Lander(velocity, altitude, fuel);

		return lander;
	}


	/* Stuff for SimulatorController to draw ugly text representation */
	public boolean getEngineStatus() {
		return lander.getEngineStatus();
	}


	public int getAltitude() {
		return (int) lander.getAltitude();
	}

	public int getFuel() {
		return (int) lander.getFuel();
	}
	
	public int getVelocity() {
		return (int) lander.getVelocity();
	}

	/* Returns true when simulation should continue. Links the AI to the lander to make stuff happen. */
	public boolean simulate() {
		boolean motor_on, result;
		if (lander == null)
			lander = newLander();
		motor_on = decideAction();
		pushLog(motor_on);
		
		result = lander.loop(motor_on);

		/* If false, simulation is over. Learn stuff and get rid of the lander */
		if (!result) {
			popLogs();
			lander = null;
		}

		return result;
	}
}
