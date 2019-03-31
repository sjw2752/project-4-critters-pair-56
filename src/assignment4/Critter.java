package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Sam Wang
 * sjw2752
 * 16215
 * Iris Ham
 * ih4548
 * 16215
 * Slip days used: <0>
 * Fall 2016
 */


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {

    private int energy = 0;

    private int x_coord;
    private int y_coord;

    private static List<Critter> population = new java.util.ArrayList<Critter>();
    private static List<Critter> babies = new java.util.ArrayList<Critter>();

    // Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
    private static String myPackage;
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    private static java.util.Random rand = new java.util.Random();

    public static int getRandomInt(int max) {
        return rand.nextInt(max);
    }

    public static void setSeed(long new_seed) {
        rand = new java.util.Random(new_seed);
    }

    /**
     * create and initialize a Critter subclass.
     * critter_class_name must be the qualified name of a concrete subclass of Critter, if not,
     * an InvalidCritterException must be thrown.
     *
     * @param critter_class_name
     * @throws InvalidCritterException
     */
    public static void createCritter(String critter_class_name) throws InvalidCritterException {
        // TODO: Complete this method
        try {
            Class critterTest = Class.forName(myPackage + "." + critter_class_name);
            if (critterTest.isAssignableFrom(Critter.class)) {
                Critter critterNew = (Critter)critterTest.newInstance();
                critterNew.energy = Params.START_ENERGY;
                critterNew.x_coord = rand.nextInt(Params.WORLD_WIDTH);
                critterNew.y_coord = rand.nextInt(Params.WORLD_HEIGHT);
                population.add(critterNew);
            }
        }
        catch (ClassNotFoundException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        catch (InstantiationException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        catch (IllegalAccessException e) {
            throw new InvalidCritterException(critter_class_name);
        }
    }

    /**
     * Gets a list of critters of a specific type.
     *
     * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
     * @return List of Critters.
     * @throws InvalidCritterException
     */
    public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
        // TODO: Complete this method
        ArrayList<Critter> critterList = new ArrayList<>();
        Class<?> testClass;

        try {
            testClass = Class.forName(critter_class_name);
        }
        catch (ClassNotFoundException e) {
            throw new InvalidCritterException(critter_class_name);
        }

        for (Critter critter : population) {
            if (testClass.isInstance(critter)) {
                critterList.add(critter);
            }
        }

        return critterList;
    }

    /**
     * Clear the world of all critters, dead and alive
     */
    public static void clearWorld() {
        // TODO: Complete this method
        population.clear();
        displayWorld();
    }

    public static void worldTimeStep() {
        // TODO: Complete this method
        //doTimeStep for each critter
        for(int i = 0; i < population.size(); i++){
            population.get(i).doTimeStep();
        }
        //remove all the dead critters from doTimeStep
        for(int i = 0; i < population.size(); i++){
            if(population.get(i).energy<=0){
                population.remove(population.get(i));
            }
        }
        //doEncounters
        Iterator<Critter> iter1 = population.iterator();
        Iterator<Critter> iter2 = population.iterator();

        while(iter1.hasNext()){
            Critter one = iter1.next();
            while(iter2.hasNext()) {
                Critter two = iter1.next();
                //if same position, do encounters
                if(one.y_coord==two.y_coord && one.x_coord == two.x_coord){
                    doEncounters(one,two);
                    //if one died, then remove
                    if(one.energy == 0){
                        iter1.remove();
                    }
                    //if two died, then remove
                    if(two.energy == 0){
                        iter2.remove();
                    }
                }
            }
        }
    }
    private static void doEncounters(Critter one, Critter two){

        boolean oneChoice = (one.fight(two.toString()));
        boolean twoChoice =(two.fight(one.toString()));

        //if both want to fight
        if(!(oneChoice == twoChoice && !oneChoice)) {
            int oneNum;
            int twoNum;
            if (oneChoice) {
                oneNum = getRandomInt(6);
            } else {
                oneNum = 0;
            }
            if (twoChoice) {
                twoNum = getRandomInt(6);
            } else {
                twoNum = 0;
            }
            //compare
            //if one wins
            if(oneNum > twoNum || oneNum == twoNum){
                one.energy = one.energy + (two.energy/2);
                two.energy = 0;
            }
            else{
                two.energy = two.energy + (one.energy/2);
                one.energy = 0;
            }
        }

    }
    public static void displayWorld() {
        // TODO: Complete this method
        String[][] world = new String[Params.WORLD_WIDTH + 2][Params.WORLD_HEIGHT + 2];

        for (int i = 0; i < Params.WORLD_WIDTH + 2; i++) {
            for (int j = 0; j < Params.WORLD_HEIGHT + 2; j++) {
                if ((i == 0 && (j == 0 || j == Params.WORLD_WIDTH + 1)) || (i == Params.WORLD_HEIGHT + 1 && (j == 0 || j == Params.WORLD_WIDTH + 1))) {
                    world[i][j] = "+";
                }
                else if (i == 0 || i == Params.WORLD_HEIGHT + 1) {
                    world[i][j] = "-";
                }
                else if (j == 0 || j == Params.WORLD_WIDTH + 1) {
                    world[i][j] = "|";
                }
                else {
                    world[i][j] = " ";
                }
            }
        }

        for (Critter critter : population) {
            world[critter.y_coord][critter.x_coord] = critter.toString();
        }

        for (int i = 0; i < Params.WORLD_WIDTH + 2; i++) {
            for (int j = 0; j < Params.WORLD_HEIGHT + 2; j++) {
                System.out.print(world[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Prints out how many Critters of each type there are on the board.
     *
     * @param critters List of Critters.
     */
    public static void runStats(List<Critter> critters) {
        System.out.print("" + critters.size() + " critters as follows -- ");
        java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
        for (Critter crit : critters) {
            String crit_string = crit.toString();
            critter_count.put(crit_string, critter_count.getOrDefault(crit_string, 0) + 1);
        }
        String prefix = "";
        for (String s : critter_count.keySet()) {
            System.out.print(prefix + s + ":" + critter_count.get(s));
            prefix = ", ";
        }
        System.out.println();
    }

    public abstract void doTimeStep();

    public abstract boolean fight(String oponent);

    /* a one-character long string that visually depicts your critter in the ASCII interface */
    public String toString() {
        return "";
    }

    protected int getEnergy() {
        return energy;
    }

    protected final void walk(int direction) {
        // TODO: Complete this method
    }

    protected final void run(int direction) {
        // TODO: Complete this method
    }

    protected final void reproduce(Critter offspring, int direction) {
        // TODO: Complete this method
    }

    /**
     * The TestCritter class allows some critters to "cheat". If you want to
     * create tests of your Critter model, you can create subclasses of this class
     * and then use the setter functions contained here.
     *
     * NOTE: you must make sure that the setter functions work with your implementation
     * of Critter. That means, if you're recording the positions of your critters
     * using some sort of external grid or some other data structure in addition
     * to the x_coord and y_coord functions, then you MUST update these setter functions
     * so that they correctly update your grid/data structure.
     */
    static abstract class TestCritter extends Critter {

        protected void setEnergy(int new_energy_value) {
            super.energy = new_energy_value;
        }

        protected void setX_coord(int new_x_coord) {
            super.x_coord = new_x_coord;
        }

        protected void setY_coord(int new_y_coord) {
            super.y_coord = new_y_coord;
        }

        protected int getX_coord() {
            return super.x_coord;
        }

        protected int getY_coord() {
            return super.y_coord;
        }

        /**
         * This method getPopulation has to be modified by you if you are not using the population
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.
         */
        protected static List<Critter> getPopulation() {
            return population;
        }

        /**
         * This method getBabies has to be modified by you if you are not using the babies
         * ArrayList that has been provided in the starter code.  In any case, it has to be
         * implemented for grading tests to work.  Babies should be added to the general population
         * at either the beginning OR the end of every timestep.
         */
        protected static List<Critter> getBabies() {
            return babies;
        }

    }

}
