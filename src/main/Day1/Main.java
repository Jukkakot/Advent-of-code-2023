package main.Day1;

import main.AOCLineIterator;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static Map<String, String> STR_TO_INT_MAP = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9");

    public static void main(String[] args) {
        try {
            AtomicInteger result = new AtomicInteger();

            new AOCLineIterator(Main.class).forEachRemaining(row -> {
                String first = getFirstNumber(row, false);
                String second = getFirstNumber(reverse(row), true);
                result.addAndGet(Integer.parseInt(first + second));
            });
            System.out.println(result.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String reverse(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    private static String getFirstNumber(String row, boolean reverse) {
        for (int i = 0; i < row.length(); i++) {
            String charAt = String.valueOf(row.charAt(i));
            try {
                return String.valueOf(Integer.parseInt(charAt));
            } catch (NumberFormatException ignored) {
                for (Map.Entry<String, String> entry : STR_TO_INT_MAP.entrySet()) {
                    if (row.startsWith(getKey(entry, reverse), i)) {
                        return entry.getValue();
                    }
                }
            }
        }
        System.out.println("No integer found");
        return null;
    }

    private static String getKey(Map.Entry<String, String> entry, boolean reverse) {
        return reverse ? reverse(entry.getKey()) : entry.getKey();
    }
}
