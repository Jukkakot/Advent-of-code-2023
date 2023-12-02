package main.DayX;

import main.AOCLineProcessor;

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
        System.out.println("Default processing");
        return line != null ? line.length() : 0;
    }
}
