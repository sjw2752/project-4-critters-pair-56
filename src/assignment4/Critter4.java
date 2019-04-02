package assignment4;

/* CRITTERS Critter4.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Sam Wang
 * sjw2752
 * 16215
 * Iris Ham
 * ih4548
 * 16215
 * Slip days used: <0>
 * Spring 2019
 */

public class Critter4 extends Critter{
    // Reproduce Critter
    // If energy is > 20, always reproduce and never fight

    @Override
    public void doTimeStep() {
        walk(0);
    }

    @Override
    public boolean fight(String opponent) {
        if (getEnergy() > 20) {
            Critter4 critterBaby = new Critter4();
            reproduce(critterBaby, getRandomInt(8));
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "4";
    }

}
