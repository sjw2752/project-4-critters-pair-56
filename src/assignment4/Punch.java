package assignment4;

import assignment4.Critter.TestCritter;
/**
 * Fighter, always fight when encounter other critters.
 */
public class Punch extends TestCritter {

	@Override
	public void doTimeStep() {
	}

	@Override
	public boolean fight(String opponent) {

		return true;
	}

	@Override
	public String toString () {
		return "Punch";
	}
}
