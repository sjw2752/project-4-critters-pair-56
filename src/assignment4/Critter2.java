package assignment4;

public class Critter2 extends Critter{
    // Up Critter
    // Always moves up

    @Override
    public void doTimeStep() {
        walk(2);
    }

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
        return "Critter2";
    }

}
