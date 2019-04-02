package assignment4;

/* CRITTERS CritterWorld.java
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

import java.util.ArrayList;

public class CritterWorld {

    private static ArrayList<Critter> critterCollection = new ArrayList<>();
    public static int timeStep;
   
    /**
     * Adds the input Critter to critterCollection.
     *
     * @param Critter the Critter to be added to the population
     */
    public static void addCritters (Critter critterNew) {
        critterCollection.add(critterNew);
    }

    /**
     * Returns the collection of Critters
     *
     * @return List of critters
     */
    public static ArrayList<Critter> getCritters () {
        return critterCollection;
    }

    /**
     * Removes all Critters in the collection.
     */
    public static void clearCollection() {
        critterCollection.removeAll(critterCollection);
    }

}
