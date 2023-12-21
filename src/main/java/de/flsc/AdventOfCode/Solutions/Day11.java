package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends AbstractPuzzleProblem<Long> {

    public Day11() {
        super("day11.txt");
    }

    @Override
    public Long solvePart1() {

        int[][] universe = createUniverse();
        return countDistances(universe, 2);

    }

    @Override
    public Long solvePart2() {

        int[][] universe = createUniverse();
        return countDistances(universe, 1000000);

    }

    private long countEmptyRowsInRange(int startY, int endY) {
        long counter = 0;
        for(int i = startY; i <= endY; i++) {
            if(isRowEmpty(getLines().get(i))) counter++;
        }
        return counter;
    }

    private long countEmptyColumnsInRange(int startX, int startY) {
        long counter = 0;
        for(int i = startX; i < startY; i++) {
            if(isColumnEmpty(i)) counter++;
        }
        return counter;
    }

    private long countDistances(int[][] universe, int expansion) {
        List<int[]> pairings = new ArrayList<>();
        List<Integer> galaxies = getGalaxies(universe);
        for(int a = 0; a < galaxies.size(); a++)
            for(int b = a+1; b < galaxies.size(); b++) pairings.add(new int[] {galaxies.get(a), galaxies.get(b)});

        long stepCounter = 0;
        for(int[] pairs : pairings) {

            int[] positionA = findPosition(universe, pairs[0]);
            int[] positionB = findPosition(universe, pairs[1]);

            long stepsX = Math.abs(positionB[0] - positionA[0]) + ((expansion-1) * countEmptyColumnsInRange(Math.min(positionA[0], positionB[0]), Math.max(positionA[0], positionB[0])));
            long stepsY = Math.abs(positionB[1] - positionA[1]) + ((expansion-1) * countEmptyRowsInRange(Math.min(positionA[1], positionB[1]), Math.max(positionA[1], positionB[1])));

            stepCounter += (stepsX+stepsY);

        }
        return stepCounter;
    }

    private int[] findPosition(int[][] universe, int galaxy) {
        for(int y = 0; y < universe.length; y++) {
            for (int x = 0; x < universe[y].length; x++)
                if (universe[y][x] == galaxy) return new int[]{x, y};
        }
        return null;
    }

    private List<Integer> getGalaxies(int[][] universe) {
        List<Integer> galaxies = new ArrayList<>();
        for(int y = 0; y < universe.length; y++)
            for(int x = 0; x < universe[y].length; x++)
                if(universe[y][x] != 0) galaxies.add(universe[y][x]);
        return galaxies;
    }

    private int[][] createUniverse() {

        int[][] universe = createUniverseBoundaries();

        int galaxyIndex = 0;
        for(int y = 0; y < getLines().size(); y++) {
            String line = getLines().get(y);
            if(isRowEmpty(line)) {
                fillEmptyRow(universe, y);
                continue;
            }
            for(int x = 0; x < line.length(); x++) {
                if(isColumnEmpty(x)) {
                    fillEmptyColumn(universe, x);
                    continue;
                }
                char c = line.charAt(x);
                if(c != '#') continue;
                universe[y][x] = ++galaxyIndex;
            }
        }
        return universe;

    }

    private void fillEmptyRow(int[][] universe, int row) {
        Arrays.fill(universe[row], 0);
    }

    private void fillEmptyColumn(int[][] universe, int column) {
        for(int y = 0; y < universe.length; y++) universe[y][column] = 0;
    }

    private int[][] createUniverseBoundaries() {
        return new int[getLines().size()+(countEmptyRows())][getLines().get(0).length()+(countEmptyColumns())];
    }

    private boolean isRowEmpty(String row) {
        return row.chars().mapToObj(c -> (char)c).filter(c -> c != '.').toList().isEmpty();
    }

    private int countEmptyRows() {
        return (int) getLines().stream().filter(this::isRowEmpty).count();
    }

    private int countEmptyColumns() {
        int counter = 0;
        for(int x = 0; x < getLines().get(0).length(); x++) if(isColumnEmpty(x)) counter++;
        return counter;
    }

    private boolean isColumnEmpty(int column) {
        for(String line : getLines())
            if(line.charAt(column) != '.') return false;
        return true;
    }

}
