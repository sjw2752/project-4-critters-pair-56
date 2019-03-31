package assignment4;

import java.util.ArrayList;

public class CritterWorld {

    private static ArrayList<Critter> critterCollection = new ArrayList<>();

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