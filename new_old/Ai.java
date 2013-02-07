public class Ai {
	int		learn_data[][][];
	Simulation	parent;


	public Ai(int max_fuel, int max_velocity, int max_altitude, Simulation simulation) {
		int i, j, k;
		
		parent = simulation;
		learn_data = new int[max_fuel][max_velocity][max_altitude];

		for (i = 0; i < max_fuel; i++)
			for (j = 0; j < max_velocity; j++)
				for (k = 0; k < max_altitude; k++)
					learn_data[i][j][k] = 0;
		return;
	}
