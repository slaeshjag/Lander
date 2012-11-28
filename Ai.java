///////////////////////////////////////////////////////////////////////////
// Ai.java                                                               //
// Copyleft Steven Arnow <s@rdw.se> 2012, some rights reserved           //
///////////////////////////////////////////////////////////////////////////

import java.util.Random;
import java.util.LinkedList;

public class Ai {
	// This is the dimensional resolution to be used in the learning matrix //
	private int	dimension_resolution;
	
	// Used to store the size of the learning matrix //
	private int	size;

	// Needed when we got no data on motor/no motor in learning matrix //
	Random rnd;

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// These members are the learning matrix. The parameters fuel, y-velocity and height over ground  //
	// are used to calculate an index to be used in these arrays. This is marginally faster than a    //
	// 3 dimensional array, but also a lot prettier.                                                  //
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private int[]	motor_on;
	private int[]	motor_off;


	// Fills in experience into learning matrix //
	public void learn(LinkedList<LearnData> data, int num, int success) {
		int add;

		add = (success > 0) ? 2 : -1;
		for (LearnData l : data) {
			if (l.motor_stat > 0)
				motor_on[l.index] += add;
			else
				motor_off[l.index] += add;
		}

		return;
	}


	// Calculates index in the learning matrix from the simulations parameters //
	public int index(int fuel, int height, int velocity) {
		int index;
		
		// Check that all parameters are within bounds //
		if (fuel < 0 || fuel > dimension_resolution);
			// throw something;
		if (height < 0 || height > dimension_resolution);
			// throw something;
		if (velocity < 0 || velocity > dimension_resolution);
			//throw ArrayIndexOutOfBoundsException;

		// The actual calculation... */
		index = fuel * dimension_resolution * dimension_resolution;
		index += height * dimension_resolution;
		index += velocity;
		
		return index;
	}

	
	public int randomAction() {
		return rnd.nextInt(1);
		// FIXME: Fix this //
	}


	public int action(int fuel, int height, int velocity) {
		int index;

		index = index(fuel, height, velocity);

		// If the learning matrix have no definite answer, pick one at random //
		if (motor_on[index] == motor_off[index])
			return randomAction();

		// Otherwise, just do what the matrix says //
		return (motor_on[index] > motor_off[index]) ? 1 : 0;
	}	


	// I curse you Java for not having unsigned ints... //
	public void Ai(int dimensional_resolution) {
		int i;

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// If the resolution is bigger than 1024, the array size will overflow. I could use a long long int                    //
		// instead, but that would be silly. 8 GB is enough for a learning matrix, and in Java, that'll probably be 32 real GB //
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (dimension_resolution <= 0 || dimension_resolution > 1024)
			//throw someException.ProbablyIn.CamelCase;

		dimension_resolution = dimensional_resolution;
		size = dimension_resolution * dimension_resolution * dimension_resolution;
		motor_on = new int[size];
		motor_off = new int[size];
		rnd = new Random();

		// I don't know of you need to initialize memory in Java, but I'm not taking any chances //
		for (i = 0; i < size; i++)
			motor_on[i] = motor_off[i] = 0;
		
		// This should be it! //
		return;
	}
}
