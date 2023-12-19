package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Day7 extends AbstractPuzzleProblem<Long> {

    public Day7() {
        super("day7.txt");
    }

    @Override
    public Long solvePart1() {
        return calculateTotalWinnings(false);
    }

    @Override
    public Long solvePart2() {
        return calculateTotalWinnings(true);
    }

    private Long calculateTotalWinnings(boolean applyJoker) {
        List<Card> cards = loadCardsToList(applyJoker);
        Collections.sort(cards);

        long totalWinnings = 0;
        for (int i = 0; i < cards.size(); i++) {
            totalWinnings += (long) cards.get(i).bid * (i + 1);
        }
        return totalWinnings;
    }

    private List<Card> loadCardsToList(boolean applyJoker) {
        ArrayList<Card> cards = new ArrayList<>();
        for (String line : getLines()) {
            String[] split = line.split(" ");
            cards.add(new Card(split[0], Integer.parseInt(split[1]), applyJoker));
        }
        return cards;
    }

    public class Card implements Comparable<Card> {

        private HashMap<Character, Integer> letterMaps = new HashMap<>();

        private final int bid, type;
        private final HashMap<Integer, Integer> cardMappings = new HashMap<>();
        private final boolean applyJoker;

        public Card(String hand, int bid, boolean applyJoker) {
            this.bid = bid;
            this.applyJoker = applyJoker;
            fillLetterMap();
            this.type = determineType(hand);
            for (int i = 0; i < hand.length(); i++) {
                char c = hand.charAt(i);
                cardMappings.put(i, Character.isDigit(c) ? Integer.valueOf(String.valueOf(c)) : letterMaps.get(c) + 9);
            }
        }

        private int determineType(String hand) {
            List<Character> singleChars = hand.chars().mapToObj(c -> (char) c).distinct().toList();
            List<Long> occurrences = new ArrayList<>();
            long amountOfJokers = 0;
            for (Character c : singleChars) {
                long charCout = hand.chars().filter(ch -> ch == c).count();
                occurrences.add(charCout);
                if(c == 'J') amountOfJokers = charCout;
            }

            if (occurrences.size() == 1) return 6;
            if (occurrences.size() == 2) {
                if(applyJoker && amountOfJokers > 0) return 6;
                if (occurrences.stream().reduce(Long::max).get() == 4) return 5;
                return 4;
            }
            if (occurrences.size() == 3) {
                if (occurrences.stream().reduce(Long::max).get() == 3) {
                    return (applyJoker && amountOfJokers > 0) ? 5 : 3;
                }
                if(applyJoker && amountOfJokers == 1) return 4;
                if(applyJoker && amountOfJokers == 2) return 5;
                return 2;
            }
            if (occurrences.size() == 4) {
                if(applyJoker && amountOfJokers > 0) return 3;
                return 1;
            }
            if(applyJoker && amountOfJokers > 0) return 1;
            return 0;
        }

        private void fillLetterMap() {
            letterMaps.put('T', 1);
            letterMaps.put('J', applyJoker ? -9 : 2);
            letterMaps.put('Q', 3);
            letterMaps.put('K', 4);
            letterMaps.put('A', 5);
        }

        @Override
        public int compareTo(Card o) {
            if (this.type > o.type) return 1;
            if (this.type < o.type) return -1;
            for (int i = 0; i < cardMappings.size(); i++) {
                if (cardMappings.get(i) > o.cardMappings.get(i)) return 1;
                if (cardMappings.get(i) < o.cardMappings.get(i)) return -1;
            }
            return 0;
        }
    }

}
