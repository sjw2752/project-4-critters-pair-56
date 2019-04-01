package assignment4;

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
