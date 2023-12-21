package de.flsc.AdventOfCode;

public class AdventOfCode {

    public static void main(String[] args) {

        solve(11);

    }

    private static void solve(int day) {
        try {
            Class.forName("de.flsc.AdventOfCode.Solutions.Day" + day).getConstructor().newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("There is no solution for day " + day + " available!");
        }
    }

}