package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.*;

public class Day9 extends AbstractPuzzleProblem<Long> {

    public Day9() {
        super("day9.txt");
    }

    @Override
    public Long solvePart1() {

        long reportValue = 0;
        for(String line : getLines()) {
            List<List<Integer>> numHistories = createNumberHistory(line);

            List<Integer> currentLine = numHistories.get(numHistories.size()-1);
            for(int i = (numHistories.size()-2); i >= 0; i--) {
                List<Integer> nextLine = new ArrayList<>(numHistories.get(i));
                nextLine.add(nextLine.get(nextLine.size()-1)+currentLine.get(currentLine.size()-1));
                numHistories.set(i, nextLine);
                currentLine = nextLine;
            }

            reportValue += currentLine.get(currentLine.size()-1);

        }
        return reportValue;

    }

    @Override
    public Long solvePart2() {
        long reportValue = 0;
        for(String line : getLines()) {
            List<List<Integer>> numHistories = createNumberHistory(line);
            List<Integer> currentLine = numHistories.get(numHistories.size()-1);
            for(int i = (numHistories.size()-2); i >= 0; i--) {
                LinkedList<Integer> nextLine = new LinkedList<>(numHistories.get(i));
                nextLine.addFirst(nextLine.get(0)-currentLine.get(0));
                numHistories.set(i, nextLine);
                currentLine = nextLine;
            }
            reportValue += currentLine.get(0);
        }
        return reportValue;
    }

    private List<List<Integer>> createNumberHistory(String line) {
        List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
        List<List<Integer>> numHistories = new ArrayList<>(Collections.singleton(numbers));

        List<Integer> currentLine = numHistories.get(0);
        while(!currentLine.stream().filter(c -> c != 0).toList().isEmpty()) {

            List<Integer> nextLine = new ArrayList<>();
            for(int i = 0; i < (currentLine.size()-1); i++) {
                nextLine.add(currentLine.get(i+1) - currentLine.get(i));
            }

            numHistories.add(nextLine);
            currentLine = nextLine;

        }
        return numHistories;
    }

}
