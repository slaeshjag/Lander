public class SimulationController {
	public static void main(String args[]) {
		int i, j, target, success_rate, altitude, fuel, velocity;
		int success, fuel_fail, crash_fail;
		boolean status;

		success = fuel_fail = crash_fail = 0;
		Ai simulation;


		if (args.length < 1) {
			System.out.println("Usage: java SimulationController <simulations>");
			System.out.println("Where simulations is the amount of simulations to run in advance before verbose output");
			return;
		}

		target = Integer.parseInt(args[0]);
		simulation = new Ai();
		success_rate = 0;

		for (i = 0; ; i++) {
			

			/* Simulation loop, iterates until simulation is over */
			for (;;) {
				if (!simulation.simulate())
					break;
				if (i < target)
					continue;
				
				try {
					Thread.sleep(200);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				/* Try to remove some flicker with VT100 codes... (Clear screen) */
				System.out.print("\033[2J");
				/* Set cursor position to top of screen */
				System.out.print("\033[;f");

				status = simulation.getEngineStatus();
				altitude = simulation.getAltitude();
				velocity = simulation.getVelocity();
				fuel = simulation.getFuel();

				/* Sometimes, it's painful to be cross platform... */
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println("===========================================");
				System.out.println("Fuel: " + fuel + ", velocity: " + velocity + ", altitude: " + altitude);
				System.out.println("Statistics: Successful: " + success + ", Out of fuel: " + fuel_fail + ", Crashed into surface: " + crash_fail);
				System.out.println("Success rate: " + success_rate);
				System.out.println("===========================================");
			
				/* Skriver ut månlandaren */
				System.out.print("##¦");
				for (j = 0; j < altitude / 8; j++)
					System.out.print(" ");
				if (status)
					System.out.print("<");
				else
					System.out.print(" ");
				System.out.println("*");
			}

			
			switch (simulation.getSuccess()) {
				case SUCCESS:
					success++;
					if (i >= target)
						System.out.println("Lander landed successfully");
					break;
				case FUEL:
					fuel_fail++;
					if (i >= target)
						System.out.println("Lander ran out of fuel");
					break;
				case CRASH:
					crash_fail++;
					if (i >= target)
						System.out.println("Lander crashed into the surface");
					break;
			}
			
			if (i >= target) {
				success_rate = (int) (((float) success) / (success + fuel_fail + crash_fail) * 100);
				
				try {
					Thread.sleep(2000);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}

				
			
