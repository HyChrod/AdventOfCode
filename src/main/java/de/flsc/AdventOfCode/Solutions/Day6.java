package de.flsc.AdventOfCode.Solutions;

import de.flsc.AdventOfCode.Utilities.AbstractPuzzleProblem;

import java.util.Arrays;
import java.util.List;

public class Day6 extends AbstractPuzzleProblem<Long> {

    public Day6() {
        super("day6.txt");
    }

    @Override
    public Long solvePart1() {

        long solution = 1;
        List<Integer> travelTimes = Arrays.stream(getLines().get(0).replace("Time:", "").split(" ")).filter(s -> !s.isBlank()).map(Integer::parseInt).toList();
        List<Integer> distances = Arrays.stream(getLines().get(1).replace("Distance:","").split(" ")).filter(d -> !d.isBlank()).map(Integer::parseInt).toList();
        for(int i = 0; i < travelTimes.size(); i++) {
            solution *= countWaysToWin(travelTimes.get(i), distances.get(i));
        }
        return solution;

    }

    @Override
    public Long solvePart2() {

        long time = Long.parseLong(getLines().get(0).replace("Time:","").replace(" ",""));
        long distance = Long.parseLong(getLines().get(1).replace("Distance:","").replace(" ",""));
        return countWaysToWin(time, distance);

    }

    private long countWaysToWin(long timeForRace, long distanceToWin) {
        long wins = 0;
        for(long t = 0; t < timeForRace; t++) {
            Boat boat = new Boat(t);
            if(boat.distanceTraveled(timeForRace-t) > distanceToWin) wins++;
        }
        return wins;
    }

    public class Boat {

        private long speed = 0;

        public Boat() {}

        public Boat(long chargeMs) {
            holdButton(chargeMs);
        }

        public void holdButton(long ms) {
            speed += ms;
        }

        public long distanceTraveled(long ms) {
            return ms*speed;
        }

    }

}
