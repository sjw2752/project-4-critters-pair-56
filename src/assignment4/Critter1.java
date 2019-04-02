package assignment4;

/* CRITTERS Critter1.java
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

public class Critter1 extends Critter {

    /**
     * Reproduces if energy is above 50
     */
    @Override
    public void doTimeStep() {
        if (getEnergy() > 50) {
            Critter1 critterBaby = new Critter1();
            reproduce(critterBaby, getRandomInt(8));
        }
    }
    /**
     * Doesn't move. Only fights if energy is <= 50
     */
    @Override
    public boolean fight(String opponent) {
        return (getEnergy() <= 50);
    }

    @Override
    public String toString() {
        return "1";
    }

}
