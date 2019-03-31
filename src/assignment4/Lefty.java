package assignment4;

import assignment4.Critter.TestCritter;

/*
critter always moves left
*/

public class Lefty extends TestCritter{

    @Override
    public void doTimeStep() {
    }

    @Override
    public boolean fight(String opponent) {
        run(getRandomInt(8));
        return false;
    }
}
