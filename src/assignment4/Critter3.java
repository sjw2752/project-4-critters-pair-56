package assignment4;

/* CRITTERS Critter3.java
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

public class Critter3 extends Critter {
    // Diagonal Left Down Critter
    // Run Diagonally Left Down


    @Override
    public void doTimeStep() {
        run(5);
    }

    @Override
    public boolean fight(String opponent) {
        return (!opponent.equals("Critter2"));
    }

    @Override
    public String toString() {
        return "3";
    }

}
