package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;

public class AOCLineIterator implements Iterator<String> {
    private final Iterator<String> iterator;

    public AOCLineIterator(Class clazz) throws FileNotFoundException {
        URL inputFile = clazz.getResource("input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile.getPath()));
        iterator = bufferedReader.lines().iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public String next() {
        return iterator.next();
    }
}
