package de.flsc.AdventOfCode.Utilities;

import java.util.ArrayList;

public abstract class AbstractPuzzleProblem<T> {

    private final FileHandler fileHandler;

    public AbstractPuzzleProblem(String fileName) {
        this.fileHandler = new FileHandler(fileName);

        System.out.println("Starting calculation..");
        System.out.println();
        long startingTime = System.currentTimeMillis();
        System.out.println("Solution to puzzle part1: " + solvePart1());
        System.out.println("Solution to puzzle part2: " + solvePart2());
        System.out.println("Time to finish " + (System.currentTimeMillis()-startingTime) + "ms");
    }

    public ArrayList<String> getLines() {
        return this.fileHandler.getLines();
    }

    public abstract T solvePart1();

    public abstract T solvePart2();

}
