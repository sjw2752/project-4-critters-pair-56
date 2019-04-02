package assignment4;

/* CRITTERS Main.java
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

import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }
        commandInterpreter(kb);

        System.out.flush();

    }
    /* Do not alter the code above for your submission. */

    private static void commandInterpreter (Scanner kb) {
        //TODO Implement this method
        System.out.print("critters> ");
        String userInput = kb.nextLine();
        String[] userInputSplit = userInput.split("\\s+");

        switch(userInputSplit[0]) {
            case "quit":
                if (userInputSplit.length > 1) {
                    System.out.println("error processing: " + userInput);
                    break;
                }
                kb.close();
                return;

            case "show":
                if (userInputSplit.length > 1) {
                    System.out.println("error processing: " + userInput);
                    break;
                }
                Critter.displayWorld();
                break;

            case "step":
                if (userInputSplit.length > 2) {
                    System.out.println("error processing: " + userInput);
                    break;
                }
                if (userInputSplit.length > 1) {
                    try {
                        CritterWorld.timeStep = Integer.parseInt(userInputSplit[1]);
                    } catch (NumberFormatException | NullPointerException nfe) {
                        System.out.println("error processing: " + userInput);
                        break;
                    }
                } else {
                    CritterWorld.timeStep = 1;
                }

                for (int i = 0; i < CritterWorld.timeStep; i++) {
                    Critter.worldTimeStep();
                }
                break;

            case "seed":
                if (userInputSplit.length != 2) {
                    System.out.println("error processing: " + userInput);
                    break;
                }

                Critter.setSeed(Long.parseLong(userInputSplit[1]));
                break;

            case "create":
                if (userInputSplit.length > 3 || userInputSplit.length < 2) {
                    System.out.println("error processing: " + userInput);
                    break;
                }

                String critterType = userInputSplit[1];
                int critterNum = 1;
                if (userInputSplit.length > 2) {
                    try {
                        critterNum = Integer.parseInt(userInputSplit[2]);
                    } catch (NumberFormatException | NullPointerException nfe) {
                        System.out.println("error processing: " + userInput);
                        break;
                    }
                }
                for (int i = 0; i < critterNum; i++) {
                    try {
                        Critter.createCritter(critterType);
                    } catch (InvalidCritterException e) {
                        System.out.println(e);
                        commandInterpreter(kb);
                    }
                }
                break;

            case "stats":
                if (userInputSplit.length != 2) {
                    System.out.println("error processing: " + userInput);
                    break;
                }
                critterType = userInputSplit[1];
                try {
                    Critter.getInstances(critterType);
                    List<Critter> critterList = Critter.getInstances(critterType);

                    try {
                        Class<?> critterClass = Class.forName(myPackage + "." + critterType);
                        Method m = critterClass.getMethod("runStats", List.class);
                        m.invoke(null, critterList);
                    }
                    catch (InvocationTargetException | ClassNotFoundException | IllegalAccessException e) {
                        System.out.println(e);
                        commandInterpreter(kb);
                    }
                    catch (NoSuchMethodException e) {
                        critterList = Critter.getInstances("Critter");
                        Critter.runStats(critterList);
                    }
                } catch (InvalidCritterException e) {
                    System.out.println("error processing: " + userInput);
                }

                break;

            case "clear":
                if (userInputSplit.length > 1) {
                    System.out.println("error processing: " + userInput);
                    break;
                }
                Critter.clearWorld();
                break;

            default:
                System.out.println("error processing: " + userInput);
                break;
        }
        commandInterpreter(kb);
    }

}
