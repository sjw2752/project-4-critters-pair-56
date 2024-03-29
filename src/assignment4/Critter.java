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
import java.lang.Math;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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

        Class<?> critterTest;
        Object object;
        Constructor<?> critterConstructor;
        try {
            critterTest = Class.forName(myPackage + "." + critter_class_name);
        } catch (ClassNotFoundException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        try {
            critterConstructor = critterTest.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        try {
            object = critterConstructor.newInstance();
        } catch (InstantiationException e) {
            throw new InvalidCritterException(critter_class_name);
        } catch (IllegalAccessException e) {
            throw new InvalidCritterException(critter_class_name);
        } catch (IllegalArgumentException e) {
            throw new InvalidCritterException(critter_class_name);
        } catch (InvocationTargetException e) {
            throw new InvalidCritterException(critter_class_name);
        }
        if (!(object instanceof Critter)) {
            throw new InvalidCritterException(critter_class_name);
        }

        Critter critterNew = (Critter)object;
        critterNew.energy = Params.START_ENERGY;
        critterNew.x_coord = (rand.nextInt(Params.WORLD_WIDTH) + 1);
        critterNew.y_coord = (rand.nextInt(Params.WORLD_HEIGHT) + 1);
        population.add(critterNew);
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
            testClass = Class.forName(myPackage + "." + critter_class_name);
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

     /**
     * stimulates one time step for each critter in the critter collection, checks for critters occupying same location.
     */
    public static void worldTimeStep() {
        // TODO: Complete this method
        //doTimeStep for each critter
        for (int i = 0; i < population.size(); i++){
            population.get(i).doTimeStep();
        }
        //remove all the dead critters from doTimeStep
        for (int i = 0; i < population.size(); i++){
            if (population.get(i).energy <= 0){
                population.remove(population.get(i));
            }
        }

        // Handling Encounters
        ArrayList<Critter> critterDead = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            Critter critter = population.get(i);
            if (!critterDead.contains(critter)) {
                Iterator<Critter> iter = population.iterator();
                while (iter.hasNext()) {
                    Critter critterCompare = iter.next();
                    if (!critterDead.contains(critterCompare) && !critter.equals(critterCompare) && critter.x_coord == critterCompare.x_coord && critter.y_coord == critterCompare.y_coord) {
                        doEncounters(critter, critterCompare);
                        if (critter.energy <= 0) {
                            critterDead.add(critter);
                            break;
                        }
                        else if (critterCompare.energy <= 0) {
                            critterDead.add(critterCompare);
                        }
                    }
                }
            }
        }

        // Removing the dead critters from population
        for(int i = 0; i < critterDead.size(); i++){
            if(population.contains(critterDead.get(i))){
                population.remove(critterDead.get(i));
            }
        }

        //subtract rest energy
        updateRestEnergy();
        //generate clovers
        genClover();
        //add babies to the population
        population.addAll(babies);
        babies.clear();
    }
    
     /**
     * Subtracts rest energy from the critters in the population and removes critters that die from this subtraction. Is called in worldTimeStep().
     */
    private static void updateRestEnergy(){
        for(Critter i : population){
            i.energy -= Params.REST_ENERGY_COST;
        }

        for (Iterator<Critter> iter = population.iterator(); iter.hasNext();) {
            if (iter.next().energy <= 0) {
                iter.remove();
            }
        }
    }
     /**
     * generates REFRESH_CLOVER_COUNT clovers. Is called in worldTimeStep().
     */
    private static void genClover(){
        for(int i = 0; i < Params.REFRESH_CLOVER_COUNT; i++){
            Critter newClover = new Clover();
            newClover.energy = Params.START_ENERGY;
            newClover.x_coord = (rand.nextInt(Params.WORLD_WIDTH) + 1);
            newClover.y_coord = (rand.nextInt(Params.WORLD_HEIGHT) + 1);
            population.add(newClover);

        }
    }
    /**
     * resolves a situation when two or more critters occupy the same location
     *
     * @param Critter two Critter objects that occupy the same locatoin
     */
    private static void doEncounters(Critter one, Critter two){

        boolean oneChoice = (one.fight(two.toString()));
        boolean twoChoice =(two.fight(one.toString()));

        //if both want to fight
        if(one.x_coord != two.x_coord || one.y_coord != two.y_coord || one.energy <= 0 || two.energy <= 0){
            return;
        }
        else
            {
            int oneNum;
            int twoNum;
            if (oneChoice) {
                oneNum = getRandomInt(one.energy);
            } else {
                oneNum = 0;
            }
            if (twoChoice) {
                twoNum = getRandomInt(two.energy);
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
    
    /**
     * Prints the world and the critters at their specific locations
     */
    public static void displayWorld() {
        // TODO: Complete this method
        String[][] world = new String[Params.WORLD_HEIGHT + 2][Params.WORLD_WIDTH + 2];

        for (int i = 0; i < Params.WORLD_HEIGHT + 2; i++) {
            for (int j = 0; j < Params.WORLD_WIDTH + 2; j++) {
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

        for (int i = 0; i < Params.WORLD_HEIGHT + 2; i++) {
            for (int j = 0; j < Params.WORLD_WIDTH + 2; j++) {
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
    /**
     * Moves the critter in a specific direction  and subtracts the walk energy cost from the Critter's energy
     *
     * @param int the direction which the critter will be moved
     */
    protected final void walk(int direction) {
        // TODO: Complete this method
        move(direction);
        this.energy = this.energy - Params.WALK_ENERGY_COST;
    }
    /**
     * Moves the critter twice in a specific direction and subtracts the specified run energy cost from the Critter's energy
     *
     * @param int the direction which the critter will be moved
     */
    protected final void run(int direction) {
        // TODO: Complete this method
        move(direction);
        move(direction);
        this.energy = this.energy - Params.RUN_ENERGY_COST;
    }

    /**
     * Changes the x and y coordinates of the Critter
     *
     * @param int the direction which determines how the x and y coordinates are changed
     */
    private void move(int direction){
        switch (direction){
            //straight right
            case 0:
                if(this.x_coord == Params.WORLD_WIDTH){
                    this.x_coord = 1;
                }
                else{
                    this.x_coord += 1;
                }
                break;
            //diag up, right
            case 1:
                if(this.x_coord == Params.WORLD_WIDTH){
                    this.x_coord = 1;
                }
                else{
                    this.x_coord += 1;
                }
                if(this.y_coord == 1){
                    this.y_coord = Params.WORLD_HEIGHT;
                }
                else{
                    this.y_coord -= 1;
                }
                break;
            //straight up
            case 2:
                if(this.y_coord == 1){
                    this.y_coord = Params.WORLD_HEIGHT;
                }
                else{
                    this.y_coord -= 1;
                }
                break;
            //diag up, left
            case 3:
                if(this.x_coord == 1){
                    this.x_coord = Params.WORLD_WIDTH;
                }
                else{
                    this.x_coord -= 1;
                }
                if(this.y_coord == 1){
                    this.y_coord = Params.WORLD_HEIGHT;
                }
                else{
                    this.y_coord -= 1;
                }
                break;
            //straight left
            case 4:
                if(this.x_coord == 1){
                    this.x_coord = Params.WORLD_WIDTH;
                }
                else{
                    this.x_coord -= 1;
                }
                break;
            //diag down left
            case 5:
                if(this.x_coord == 1){
                    this.x_coord = Params.WORLD_WIDTH;
                }
                else{
                    this.x_coord -= 1;
                }
                if(this.y_coord == Params.WORLD_HEIGHT){
                    this.y_coord = 1;
                }
                else{
                    this.y_coord += 1;
                }
                break;
            //straight down
            case 6:
                if(this.y_coord == Params.WORLD_HEIGHT){
                    this.y_coord = 1;
                }
                else{
                    this.y_coord += 1;
                }
                break;
            //diag down right
            case 7:
                if(this.x_coord == Params.WORLD_WIDTH){
                    this.x_coord = 1;
                }
                else{
                    this.x_coord += 1;
                }
                if(this.y_coord == Params.WORLD_HEIGHT){
                    this.y_coord = 1;
                }
                else{
                    this.y_coord += 1;
                }
                break;
        }
    }

    /**
     * The offspring will be adjacent to the parent and half its parent's
     * energy (rounded down) while the parent loses half of its
     * energy(rounded up). Only occurs if the parent has enough energy
     *
     * @param Critter Critter object that will be modified with the offspring's data given the reproduction was successful
     * @param int the direction which the offspring will move to be adjacent to the parent
     */
    protected final void reproduce(Critter offspring, int direction) {
        // TODO: Complete this method
        //check if parent has energy
        if (this.energy > Params.MIN_REPRODUCE_ENERGY) {
            offspring.energy = this.energy / 2;
            this.energy = Math.round(this.energy / (float)2);
            offspring.x_coord = this.x_coord;
            offspring.y_coord = this.y_coord;
            offspring.move(direction);
            babies.add(offspring);
        }
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
