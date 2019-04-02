package assignment4;

/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Sam Wang
 * sjw2752
 * 16215
 * Iris Ham
 * ih4548
 * 16215
 * Slip days used: <0>
 * Fall 2016
 */


public class Critter1 extends Critter {
    // Gambler Critter
    // fights if energy is <= half energy
    // doesn't move

    @Override
    public void doTimeStep() {
        if (getEnergy() > 50) {
            Critter1 critterBaby = new Critter1();
            reproduce(critterBaby, getRandomInt(8));
        }
    }

    @Override
    public boolean fight(String opponent) {
        return (getEnergy() <= 50);
    }

    @Override
    public String toString() {
        return "1";
    }

}
