package assignment4;

import java.util.List;

public class MyCritter9 extends Critter.TestCritter {

	@Override
	public void doTimeStep() {
		walk(0);
		walk(0);
	}

	@Override
	public boolean fight(String opponent) {
		if (getEnergy() > 10) return true;
		return false;
	}
	
	public String toString() {
		return "1";
	}
	
	public void test (List<Critter> l) {
		
	}
}
