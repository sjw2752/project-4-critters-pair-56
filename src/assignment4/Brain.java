package assignment4;

import java.util.*;
/**
 * Wise critter, fight only when it has energy
 */
public class Brain extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}

	public String toString() {
		return "Brain";
	}

	public void test (List<Critter> l) {

	}
}
