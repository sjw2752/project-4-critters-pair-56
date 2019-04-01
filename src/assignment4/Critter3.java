package assignment4;

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
        return "Critter3";
    }

}
