package assignment4;

import java.util.ArrayList;

public class CritterWorld {

    private ArrayList<Critter> critterCollection = new ArrayList<>();

    private void addCritters (Critter critterNew) {
        critterCollection.add(critterNew);
    }

    private ArrayList<Critter> getCritters () {
        return critterCollection;
    }

}
