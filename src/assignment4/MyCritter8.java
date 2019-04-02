package assignment4;

import assignment4.Critter.TestCritter;

public class MyCritter8 extends TestCritter {
	
	@Override
	public void doTimeStep() {
		reproduce(new MyCritter8(), 0);
	}

	@Override
	public boolean fight(String opponent) {
		return true;
	}

	@Override
	public String toString () {
		return "8";
	}
}
