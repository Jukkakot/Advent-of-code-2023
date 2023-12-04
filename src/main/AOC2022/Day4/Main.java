package main.AOC2022.Day4;

import main.AOCLineProcessor;
import main.Utils;

public class Main {
    public static void main(String[] args) {
        new AOCLineProcessor(Main.class) {
            @Override
            public int processLine(String line) {
                return doYourThing(line);
            }
        }.processLines();
    }

    private static int doYourThing(String line) {
        String[] split = line.split(",");
        String[] r1SplitStr = split[0].split("-");
        int[] range1 = new int[]{Integer.parseInt(r1SplitStr[0]), Integer.parseInt(r1SplitStr[1])};
        String[] r2SplitStr = split[1].split("-");
        int[] range2 = new int[]{Integer.parseInt(r2SplitStr[0]), Integer.parseInt(r2SplitStr[1])};
        return Utils.rangesOverlap(range1, range2) ? 1 : 0;
    }
}
