package assignment4;

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
