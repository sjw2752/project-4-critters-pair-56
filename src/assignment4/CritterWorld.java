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

    public static void addCritters (Critter critterNew) {
        critterCollection.add(critterNew);
    }

    public static ArrayList<Critter> getCritters () {
        return critterCollection;
    }

    public static void clearCollection() {
        critterCollection.removeAll(critterCollection);
    }

}
