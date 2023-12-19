package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day5 extends AbstractPuzzleProblem<Long> {

    public Day5() {
        super("day5.txt");
    }

    @Override
    public Long solvePart1() {
        Mapper mapper = new Mapper();

        List<Long> seeds = new ArrayList<>();
        seeds.addAll(Arrays.stream(getLines().get(0).replace("seeds:","").split(" ")).filter(a -> !a.isBlank()).map(Long::parseLong).toList());

        Long lowestLocation = Long.MAX_VALUE;
        for(Long sds : seeds)
            lowestLocation = Math.min(lowestLocation, mapper.getLocation(sds));

        return lowestLocation;

    }

    @Override
    public Long solvePart2() {
        Mapper mapper = new Mapper();

        long lowestLocation = Long.MAX_VALUE;
        String[] split = getLines().get(0).replace("seeds:","").split(" ");

        for(int i = 1; i < split.length; i=i+2) {

            for(long l = 0L; l < Long.parseLong(split[i+1]); l++) {
                long seed = Long.parseLong(split[i])+l;
                lowestLocation = Long.min(lowestLocation, mapper.getLocation(seed));
            }
        }
        return lowestLocation;

    }

    private class Mapper {

        private final HashMap<Integer, List<Mapping>> mappings = new HashMap<>();

        public Mapper() {
            int section = -1;
            for(String line : getLines()) {
                if(line.startsWith("seeds:")) continue;
                if(line.isBlank()) {
                    section++;
                    continue;
                }
                if(Character.isDigit(line.charAt(0))) {
                    String[] split = line.split(" ");
                    List<Mapping> maps = mappings.containsKey(section) ? mappings.get(section) : new ArrayList<>();
                    maps.add(new Mapping(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
                    mappings.put(section, maps);
                }
            }
        }

        public Long getLocation(Long value) {
            for(int key : mappings.keySet()) {
                List<Mapping> listMappings = mappings.get(key);
                for(Mapping maps : listMappings) {
                    if(maps.matchesSource(value)) {
                        value = maps.getMatchingDestination(value);
                        break;
                    }
                }
            }
            return value;
        }

        private class Mapping {

            private final long destination, source, range;

            public Mapping(long destination, long source, long range) {
                this.destination = destination;
                this.source = source;
                this.range = range;
            }

            public boolean matchesSource(long value) {
                return value >= source && value < (source+range);
            }

            public long getMatchingDestination(long value) {
                return destination + (value-source);
            }

        }

    }

}
