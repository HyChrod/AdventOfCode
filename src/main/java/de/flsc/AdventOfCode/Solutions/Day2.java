package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.ArrayList;

public class Day2 extends AbstractPuzzleProblem<Integer> {
    public Day2() {
        super("day2.txt");
    }

    @Override
    public Integer solvePart1() {
        ArrayList<Integer[]> assignment = createAssignment();
        int sum = 0;
        for(int i = 0; i < assignment.size(); i++) {
            Integer[] colorValues = assignment.get(i);
            if(colorValues[0] > 12 || colorValues[1] > 13 || colorValues[2] > 14) continue;
            sum += (i+1);
        }
        return sum;
    }

    @Override
    public Integer solvePart2() {
        ArrayList<Integer[]> assignment = createAssignment();
        int sum = 0;
        for(Integer[] colorValues : assignment) {
            sum += (colorValues[0]*colorValues[1]*colorValues[2]);
        }
        return sum;
    }

    private ArrayList<Integer[]> createAssignment() {
        ArrayList<Integer[]> assignment = new ArrayList<>();
        for(String line : getLines()) {
            line = line.replace(" ", "").split(":")[1];
            int red = 0, green = 0, blue = 0;
            for(String subGame : line.split(";")) {
                for(String color : subGame.split(",")) {
                    if(color.contains("red")) {
                        red = Math.max(red, Integer.parseInt(color.replace("red","")));
                    } else if(color.contains("green")) {
                        green = Math.max(green, Integer.parseInt(color.replace("green", "")));
                    } else {
                        blue = Math.max(blue, Integer.parseInt(color.replace("blue","")));
                    }
                }
            }
            assignment.add(new Integer[] {red, green, blue});
        }
        return assignment;
    }

}
