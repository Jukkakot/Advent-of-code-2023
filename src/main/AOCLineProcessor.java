package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class AOCLineProcessor {
    private Iterator<String> iterator;

    public AOCLineProcessor(Class clazz) {
        try {
            URL inputFile = clazz.getResource("input.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile.getPath()));
            iterator = bufferedReader.lines().iterator();
        } catch (Exception ignored) {
        }
    }

    public Iterator<String> getIterator() {
        return iterator;
    }

    public void processLines() {
        AtomicInteger result = new AtomicInteger();
        getIterator().forEachRemaining(line -> {
            result.getAndAdd(processLine(line));
        });
        System.out.println("Result: " + result.get());
    }

    protected int processLine(String line) {
        System.out.println(line);
        return 0;
    }
}
