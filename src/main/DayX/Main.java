package main.DayX;

import main.AOCLineIterator;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        AtomicInteger result = new AtomicInteger();
        new AOCLineIterator(main.Day2.Main.class).forEachRemaining(line -> {
            result.getAndAdd(doYourThing(line));
        });
        System.out.println("Result: "+ result.get());
    }

    private static int doYourThing(String line) {
        return line != null ? line.length() : 0;
    }
}
