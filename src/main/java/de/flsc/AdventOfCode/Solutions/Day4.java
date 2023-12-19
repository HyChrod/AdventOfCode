package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day4 extends AbstractPuzzleProblem<Integer> {

    public Day4() {
        super("day4.txt");
    }

    @Override
    public Integer solvePart1() {

        int totalPoints = 0;
        for(String line : getLines()) {

            Scratchcard card = new Scratchcard(line);
            int cardPoints = 0;
            for(Integer owningNumbers : card.getOwningNumbers()) {
                if(card.isWinningNumber(owningNumbers)) cardPoints = cardPoints == 0 ? 1 : cardPoints*2;
            }
            totalPoints += cardPoints;

        }
        return totalPoints;

    }

    @Override
    public Integer solvePart2() {

        HashMap<Integer, Scratchcard> scratchCards = new HashMap<>();
        for(int i = 0; i < getLines().size(); i++) scratchCards.put(i, new Scratchcard(getLines().get(i)));

        for(int key : scratchCards.keySet()) {
            Scratchcard card = scratchCards.get(key);
            for(int i = 1; i <= card.getMatchingNumbers(); i++) scratchCards.get(key+i).addCopies(card.getCopies());
        }
        return scratchCards.values().stream().map(Scratchcard::getCopies).reduce(Integer::sum).get();

    }

    private class Scratchcard {

        private final List<Integer> winningNumbers = new ArrayList<>();
        private final List<Integer> owningNumbers = new ArrayList<>();

        private int matchingNumbers = 0;
        private int copies = 1;

        public Scratchcard(String line) {
            line = line.split(":")[1];
            String winners = line.split("\\|")[0];
            String owners = line.split("\\|")[1];

            for(String winNum : winners.split(" ")) {
                if(winNum.isBlank()) continue;
                winningNumbers.add(Integer.parseInt(winNum));
            }
            for(String ownNum : owners.split(" ")) {
                if(ownNum.isBlank()) continue;
                int num = Integer.parseInt(ownNum);
                owningNumbers.add(num);
                if(winningNumbers.contains(num)) matchingNumbers++;
            }
        }

        public boolean isWinningNumber(Integer owningNumber) {return this.winningNumbers.contains(owningNumber);}
        public List<Integer> getOwningNumbers() {return this.owningNumbers;}
        public int getMatchingNumbers() {return this.matchingNumbers;}
        public int getCopies() {return this.copies;}
        public void addCopies(int toAdd) {this.copies += toAdd;}

    }
}