public class SimulationController {
	public static void main(String args[]) {
		int i, target, success_rate;
		int j;

		int success, fuel_fail, crash_fail;

		success = fuel_fail = crash_fail = 0;
		Ai simulation;


		if (args.length < 1) {
			System.out.println("Usage: java SimulationController.class <simulations>");
			System.out.println("Where simulations is the amount of simulations to run in advance before verbose output");
			return;
		}

		target = Integer.parseInt(args[0]);
		simulation = new Ai();

		for (i = 0; ; i++) {
			if (i >= target) {
				success_rate = (int) (((float) success) / (success + fuel_fail + crash_fail) * 100);
				System.out.println("Simulation attempt #" + i + " starting now");
				System.out.println("Statistics: Successful: " + success + ", Out of fuel: " + fuel_fail + ", Crashed into surface: " + crash_fail);
				System.out.println("Success rate: " + success_rate);
			}

			/* Simulation loop, iterates until simulation is over */
			for (j = 0;; j++)
				if (!simulation.simulate())
					break;
			
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

			if (i == target)
				return;
		}
	}
}

				
			
