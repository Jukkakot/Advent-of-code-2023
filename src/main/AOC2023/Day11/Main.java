package main.AOC2023.Day11;

import main.AOCLineProcessor;
import main.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    static int index = 0;
    static List<List<Character>> MAP = new ArrayList<>();
    static List<int[]> GALAXY_COORDS = new ArrayList<>();
    static int WIDTH = 0;
    static int HEIGHT = 0;
    static int EXPANSION_MULTIPLIER = 1000000;

    public static void main(String[] args) {
        new AOCLineProcessor(Main.class).getIterator().forEachRemaining(Main::doYourThing);
        HEIGHT = index / WIDTH;
//        printMap();
//        System.out.println("Galaxy indexes " + GALAXY_INDEXES);
        List<Integer> galaxyRows = GALAXY_COORDS.stream().map(ints -> ints[1]).toList();
        List<Integer> galaxyColumns = GALAXY_COORDS.stream().map(ints -> ints[0]).toList();
        List<Integer> notGalaxyRows = IntStream.range(0, WIDTH - 1).boxed().filter(i -> !galaxyRows.contains(i)).toList();
        List<Integer> notGalaxyColumns = IntStream.range(0, WIDTH - 1).boxed().filter(i -> !galaxyColumns.contains(i)).toList();
        System.out.println("Not galaxy rows " + notGalaxyRows);
        System.out.println("Not galaxy columns " + notGalaxyColumns);
        for (Integer row : notGalaxyRows) {
            MAP.addAll(row + (notGalaxyRows.indexOf(row) * EXPANSION_MULTIPLIER), Utils.createListOfLists(new ArrayList<>(IntStream.range(0, WIDTH)
                    .mapToObj(i -> '.')
                    .collect(Collectors.toList())), EXPANSION_MULTIPLIER));
        }

        int heightExpansion = notGalaxyRows.size() * EXPANSION_MULTIPLIER;
        HEIGHT += heightExpansion;
        for (Integer column : notGalaxyColumns) {
            for (int i = 0; i < HEIGHT; i++) {
                MAP.get(i).addAll(column + (notGalaxyColumns.indexOf(column) * EXPANSION_MULTIPLIER), new ArrayList<>(Collections.nCopies(EXPANSION_MULTIPLIER, '.')));
            }
        }
        int widthExpansion = notGalaxyColumns.size() * EXPANSION_MULTIPLIER;
        WIDTH += widthExpansion;
//        printMap();
        GALAXY_COORDS = new ArrayList<>();
        for (int rIndex = 0; rIndex < MAP.size(); rIndex++) {
            List<Character> row = MAP.get(rIndex);
            for (int cIndex = 0; cIndex < row.size(); cIndex++) {
                if (row.get(cIndex).equals('#')) {
                    GALAXY_COORDS.add(new int[]{cIndex, rIndex});
                }
            }
        }
        GALAXY_COORDS.forEach(ints -> System.out.println(Arrays.toString(ints)));
        printMap();
        int rounds = 0;
        int result = 0;
        for (List<int[]> galaxyPairs : Utils.getUniquePairs(GALAXY_COORDS)) {
            int[] pointA = galaxyPairs.get(0);
            int[] pointB = galaxyPairs.get(1);
            int delta = Utils.shortestPath(pointA, pointB);
//            System.out.println(Arrays.toString(pointA) + " " + Arrays.toString(pointB));
//            System.out.println("From " + (GALAXY_COORDS.indexOf(pointA) + 1) + " to " + (GALAXY_COORDS.indexOf(pointB) + 1) + " distance: " + delta);
//            System.out.println();
            result += delta;
            rounds++;
        }
        System.out.println("Rounds " + rounds);
        System.out.println("Result: " + result);
    }


    private static void printMap() {
        System.out.println("W,H: " + MAP.get(0).size() + "," + MAP.size());
        MAP.forEach(row -> {
            AtomicInteger columnIndex = new AtomicInteger(); // Variable to keep track of the current character's index

            row.forEach(character -> {
                if ('#' == character) {
                    try {
                        List<int[]> galaxyCoords = GALAXY_COORDS.stream().filter(ints -> {
                            int rowIndex = MAP.indexOf(row);
                            return Utils.arraysMatch(ints, new int[]{columnIndex.get(), rowIndex});
                        }).toList();
                        System.out.print(GALAXY_COORDS.indexOf(galaxyCoords.get(0)) + 1);
                    } catch (Exception e) {
                        // Handle exception
                        System.out.print(character);
                    }
                } else {
                    System.out.print(character);
                }

                columnIndex.getAndIncrement(); // Increment the character's index within the row
            });
            System.out.println();
        });
    }

    private static void doYourThing(String line) {
        if (WIDTH == 0) {
            WIDTH = line.length();
        }
        MAP.add(new ArrayList<>(line.chars().mapToObj(e -> (char) e).toList()));
        for (Character c : line.toCharArray()) {
            if (c == '#') {
                GALAXY_COORDS.add(Utils.get2DCoordinates(index, WIDTH));
            }
            index++;
        }

    }
}
