public class UnitTest {
	public static void main(String args[]) {
		Lander lander;

		/* Lander(velocity, altitude, fuel); */
		
		
		System.out.print("Making sure that out-of-fuel is detected... ");
		lander = new Lander(0, 100, 0);
		if (lander.loop(false))
			System.out.println("FAIL");
		else {
			if (lander.landerOutcome() != Lander.landerResult.SIMULATION_FUEL_FAIL)
				System.out.println("FAIL");
			else
				System.out.println("PASS");
		}

		System.out.print("Making sure that crash-into-the-ground is detected... ");
		lander = new Lander(-50, 1, 50);
		if (lander.loop(false))
			System.out.println("FAIL_");
		else {
			if (lander.landerOutcome() != Lander.landerResult.SIMULATION_CRASH_FAIL)
				System.out.println("FAIL");
			else
				System.out.println("PASS");
		}

		System.out.print("Making sure that a successful landing is detected... ");
		lander = new Lander(-1, 1, 50);
		if (lander.loop(false))
			System.out.println("FAIL");
		else {
			if (lander.landerOutcome() != Lander.landerResult.SIMULATION_SUCCESS)
				System.out.println("FAIL");
			else
				System.out.println("PASS");
		}

		return;
	}
}
