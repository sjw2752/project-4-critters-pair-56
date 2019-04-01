package assignment4;

import java.util.List;

public class Critter1 extends Critter {
    // Heart Attack Critter

    @Override
    public void doTimeStep() {
        walk(0);
    }

    @Override
    public boolean fight(String opponent) {

    }

    public String toString() {
        return "Brain";
    }

}
