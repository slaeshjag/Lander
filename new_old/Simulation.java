
public class Simulation {
	/* This is our action matrix. If bigger than 0, turn motor on. If smaller than 0, turn motor off. If 0, do something random. */
	/* First param is fuel level, second param is velocity and third param is altitude over the surface */
	int		fuel_max;
	int		velocity_max;
	int		altitude_max;

	Physics		physics;

	Simulation(int fuel_granuality, int velocity_granuality, int altitude_granuality, Physics physics_rules) {
		fuel_max = fuel_granuality;
		velocity_max = velocity_granuality;
		altitude_max = altitude_granuality;
		physics = physics_rules;

		return;
	}
}
