package main.AOC2023.Day2;

import main.AOCLineProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static Map<String, Integer> LIMITS = Map.of("red", 12, "green", 13, "blue", 14);

    public static void main(String[] args) {
        new AOCLineProcessor(Main.class) {
            @Override
            public int processLine(String line) {
                return doYourThing(line);
            }
        }.processLines();
    }

    private static int doYourThing(String line) {
        int gameId = Integer.parseInt(line.strip().split(" ")[1].split(":")[0]);
        List<String> reveals = List.of(line.split(":")[1].split(";"));
        System.out.println("Game " + gameId + " " + reveals);
        List<Map<String, Integer>> revealList = reveals.stream().map(Main::getRevealMap).toList();
        int power = 1;
        for (Integer cubeCount : getMinimumCubeMap(revealList).values()) {
            power *= cubeCount;
        }
        return power;
    }

    private static Map<String, Integer> getMinimumCubeMap(List<Map<String, Integer>> revealList) {
        int minRed = 0;
        int minBlue = 0;
        int minGreen = 0;
        for (Map<String, Integer> reveal : revealList) {
            Integer red = reveal.get("red");
            if (red != null && minRed < red) {
                minRed = red;
            }
            Integer blue = reveal.get("blue");
            if (blue != null && minBlue < blue) {
                minBlue = blue;
            }
            Integer green = reveal.get("green");
            if (green != null && minGreen < green) {
                minGreen = green;
            }
        }

        return Map.of("red", minRed, "green", minGreen, "blue", minBlue);
    }

    private static Map<String, Integer> getRevealMap(String revealStr) {
        Map<String, Integer> cubeReveal = new HashMap<>();
        for (String cube : revealStr.strip().split(",")) {
            String[] split = cube.strip().split(" ");
            String color = split[1].strip();
            int count = Integer.parseInt(split[0]);
            Integer cubeCount = cubeReveal.get(color);
            if (cubeCount != null) {
                cubeCount += count;
                cubeReveal.put(color, cubeCount);
            } else {
                cubeReveal.put(color, count);
            }
        }
        return cubeReveal;
    }

    private static boolean isPossible(Map<String, Integer> reveal) {
        for (Map.Entry<String, Integer> cubeLimit : reveal.entrySet()) {
            Integer limitForColor = LIMITS.get(cubeLimit.getKey());
            if (cubeLimit.getValue() > limitForColor) {
//                System.out.println("Too many " + cubeLimit.getKey() + " colored cubes " + reveal);
                return false;
            }
        }
//        System.out.println("Possible "  +reveal);
        return true;
    }
}
