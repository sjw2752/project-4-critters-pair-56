package assignment4;

import assignment4.Critter.TestCritter;

public class MyCritter4 extends TestCritter {

	@Override
	// Runs in a random direction
	public void doTimeStep() {
		run(getRandomInt(8));
	}

	@Override
	public boolean fight(String opponent) {
		// TODO Auto-generated method stub
		return false;
	}

}
