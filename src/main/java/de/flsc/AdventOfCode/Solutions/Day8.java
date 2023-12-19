package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.HashMap;
import java.util.List;

public class Day8 extends AbstractPuzzleProblem<Long> {

    public Day8() {
        super("day8.txt");
    }

    @Override
    public Long solvePart1() {

        Node node = new Node("AAA", createMapping(), false);
        List<String> directions = getLines().get(0).replace(" ","").chars().mapToObj(c -> String.valueOf((char)c)).toList();
        return node.follow(directions);

    }

    @Override
    public Long solvePart2() {

        HashMap<String, HashMap<String, String>> mappings = createMapping();
        List<String> directions = getLines().get(0).replace(" ","").chars().mapToObj(c -> String.valueOf((char)c)).toList();
        List<String> startingNodes = mappings.keySet().stream().filter(s -> s.endsWith("A")).toList();
        List<Long> counts = startingNodes.stream().map(n -> new Node(n, mappings, true).follow(directions)).toList();
        return counts.stream().reduce(1L, (x,y) -> x * (y / gcd(x, y)));

    }

    private long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    private HashMap<String, HashMap<String, String>> createMapping() {

        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        for(int i = 2; i < getLines().size(); i++) {

            String[] splitted = getLines().get(i).replace(" ","").replace("(","").replace(")","").split("=");
            String node = splitted[0];
            String[] directions = splitted[1].split(",");
            String left = directions[0];
            String right = directions[1];

            HashMap<String, String> directionMap = new HashMap<>();
            directionMap.put("L", left);
            directionMap.put("R", right);
            map.put(node, directionMap);

        }
        return map;

    }

    public class Node {

        private final String start;
        private final boolean simultaneous;
        private final HashMap<String, HashMap<String, String>> network ;

        public Node(String start, HashMap<String, HashMap<String, String>> network, boolean simultaneous) {
            this.start = start;
            this.network = network;
            this.simultaneous = simultaneous;
        }

        public long follow(List<String> directions) {
            long stepCounter = 0;
            int listIndex = 0;
            String node = start;
            HashMap<String, HashMap<String, String>> mapping = createMapping();
            while(!(simultaneous ? node.endsWith("Z") : node.equals("ZZZ"))) {
                String direction = directions.get(listIndex);
                node = mapping.get(node).get(direction);
                stepCounter++;
                listIndex = listIndex == (directions.size()-1) ? 0 : listIndex+1;
            }
            return stepCounter;
        }
    }

}
