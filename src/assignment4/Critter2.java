package assignment4;

/* CRITTERS Critter2.java
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

public class Critter2 extends Critter{

    /**
     * Walks straight up 
     */
    @Override
    public void doTimeStep() {
        walk(2);
    }

    /**
     * Reproduces if Critter's energy is >= 150, walks straight up, and never wants to fight
     */
    @Override
    public boolean fight(String opponent) {
        if (getEnergy() >= 150) {
            Critter2 critterBaby = new Critter2();
            reproduce(critterBaby, getRandomInt(8));
        }
        walk(2);
        return false;
    }

    @Override
    public String toString() {
        return "2";
    }

}
