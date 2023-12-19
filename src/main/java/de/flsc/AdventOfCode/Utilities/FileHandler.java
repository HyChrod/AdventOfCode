package de.flsc.AdventOfCode.Utilities;

import de.flsc.AdventOfCode.AdventOfCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class FileHandler {

    private final String fileName;
    private final ArrayList<String> lines = new ArrayList<>();

    public FileHandler(String fileName) {
        this.fileName = fileName;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(AdventOfCode.class.getResourceAsStream("/" + fileName))))) {

            String line = "";
            while((line = reader.readLine()) != null) lines.add(line);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<String> getLines() {return this.lines;}

}
