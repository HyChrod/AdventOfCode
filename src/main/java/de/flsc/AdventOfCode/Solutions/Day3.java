package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.ArrayList;

public class Day3 extends AbstractPuzzleProblem<Long> {

    public Day3() {
        super("day3.txt");
    }

    @Override
    public Long solvePart1() {

        Character[][] matrix = createMatrix();
        int sum = 0;

        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix.length; y++) {

                Character c = matrix[x][y];
                if(c != null && Character.isDigit(c)) {

                    if((x > 0 && matrix[x-1][y] != null && !Character.isDigit(matrix[x-1][y]))
                    || (x > 0 && y > 0 && matrix[x-1][y-1] != null && !Character.isDigit(matrix[x-1][y-1]))
                    || (y > 0 && matrix[x][y-1] != null && !Character.isDigit(matrix[x][y-1]))
                    || (x < (matrix.length-1) && y > 0 && matrix[x+1][y-1] != null && !Character.isDigit(matrix[x+1][y-1]))
                    || (x < (matrix.length-1) && matrix[x+1][y] != null && !Character.isDigit(matrix[x+1][y]))
                    || (x < (matrix.length-1) && y < (matrix.length-1) && matrix[x+1][y+1] != null && !Character.isDigit(matrix[x+1][y+1]))
                    || (y < (matrix.length-1) && matrix[x][y+1] != null && !Character.isDigit(matrix[x][y+1]))
                    || (x > 0 && y < (matrix.length-1) && matrix[x-1][y+1] != null && !Character.isDigit(matrix[x-1][y+1]))) {

                        int[] values = findNumberWithEnd(matrix, x, y);
                        sum += values[0];
                        y = values[1];

                    }

                }

            }
        }
        return (long) sum;

    }

    @Override
    public Long solvePart2() {
        Character[][] matrix = createMatrix();
        long sum = 0;

        for(int y = 0; y < matrix.length; y++) {
            for(int x = 0; x < matrix.length; x++) {

                Character c = matrix[y][x];
                if(c != null && c == '*') {

                    ArrayList<Integer> numbers = new ArrayList<>();
                    checkForGearRatio(matrix, y, x-1, numbers);
                    checkForGearRatio(matrix, y-1, x-1, numbers);
                    checkForGearRatio(matrix, y-1, x, numbers);
                    checkForGearRatio(matrix, y-1, x+1, numbers);
                    checkForGearRatio(matrix, y, x+1, numbers);
                    checkForGearRatio(matrix, y+1, x+1, numbers);
                    checkForGearRatio(matrix, y+1, x, numbers);
                    checkForGearRatio(matrix, y+1, x-1, numbers);
                    if(numbers.size() == 2) sum += numbers.stream().reduce((a,b) -> a*b).get();

                }

            }
        }
        return sum;

    }

    private void checkForGearRatio(Character[][] matrix, int y, int x, ArrayList<Integer> numbers) {
        if(matrix[y][x] != null && Character.isDigit(matrix[y][x])) {
            int number = findNumberWithEnd(matrix, y, x)[0];
            if(!numbers.contains(number)) numbers.add(number);
        }
    }

    private int[] findNumberWithEnd(Character[][] matrix, int x, int y) {
        String number = ""+matrix[x][y];
        int tempY = y-1;
        while(tempY >= 0 && matrix[x][tempY] != null && Character.isDigit(matrix[x][tempY])) {
            number = matrix[x][tempY] + number;
            tempY--;
        }
        tempY = y+1;
        while(tempY < matrix.length && matrix[x][tempY] != null && Character.isDigit(matrix[x][tempY])) {
            number = number + matrix[x][tempY];
            tempY++;
        }
        return new int[] {Integer.parseInt(number),tempY};
    }

    private Character[][] createMatrix() {
        Character[][] matrix = new Character[getLines().size()][getLines().get(0).length()];
        for(int line = 0; line < getLines().size(); line++) {
            for(int c = 0; c < getLines().get(line).length(); c++) {
                char cChar = getLines().get(line).charAt(c);
                matrix[line][c] = cChar == '.' ? null : cChar;
            }
        }
        return matrix;
    }

}
