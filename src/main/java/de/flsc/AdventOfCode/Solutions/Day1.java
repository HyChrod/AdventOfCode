package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.HashMap;

public class Day1 extends AbstractPuzzleProblem<Integer> {

    public Day1() {
        super("day1.txt");
    }

    @Override
    public Integer solvePart1() {
        int sum = 0;
        for(String line : getLines()) sum += findCalibrationNumber(line);
        return sum;
    }

    @Override
    public Integer solvePart2() {
        HashMap<String, String> replacementNumbers = new HashMap<>();
        replacementNumbers.put("one", "o1e");
        replacementNumbers.put("two", "t2o");
        replacementNumbers.put("three", "t3e");
        replacementNumbers.put("four", "f4r");
        replacementNumbers.put("five", "f5e");
        replacementNumbers.put("six", "s6x");
        replacementNumbers.put("seven", "s7n");
        replacementNumbers.put("eight", "e8t");
        replacementNumbers.put("nine", "n9e");

        int sum = 0;
        for(String line : getLines()) {
            for(String key : replacementNumbers.keySet()) line = line.replace(key, replacementNumbers.get(key));
            sum += findCalibrationNumber(line);
        }
        return sum;
    }

    private int findCalibrationNumber(String line) {
        return Integer.parseInt(findFirstNumber(line) + findFirstNumber(new StringBuilder(line).reverse().toString()));
    }

    private String findFirstNumber(String line) {
        for(int i = 0; i < line.length(); i++) {
            if(Character.isDigit(line.charAt(i)))
                return String.valueOf(line.charAt(i));
        }
        return null;
    }

}
