package main.AOC2023.Day4;

import main.AOCLineProcessor;
import main.Utils;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static List<Integer> CARD_MATCHES = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Start processing cards");
        Instant start = Instant.now();
        new AOCLineProcessor(Main.class).getIterator().forEachRemaining(Main::doYourThing);
        Instant end = Instant.now();
        System.out.println("Finished processing cards " + Duration.between(start, end).toMillis());

        start = Instant.now();
        int result = CARD_MATCHES.size();
        for (int cardId = 0; cardId < CARD_MATCHES.size(); cardId++) {
            int cardCount = getCardCount(cardId);
            result += cardCount;
        }
        end = Instant.now();
        System.out.println("Finished counting card stacks " + Duration.between(start, end).toMillis());
        System.out.println("Result: " + result);
    }

    private static int getCardCount(int cardId) {
        if (cardId >= CARD_MATCHES.size()) {
            return 0;
        }
        int cardMatches = CARD_MATCHES.get(cardId);
        if (cardMatches > 0) {
            for (int i = cardId + 1; i < cardId + CARD_MATCHES.get(cardId) + 1; i++) {
                cardMatches += getCardCount(i);
            }
        }
        return cardMatches;
    }

    private static void doYourThing(String line) {
        String[] split = line.split(":")[1].split("\\|");

        int matchingElements = getMatchingElements(split);

        CARD_MATCHES.add(matchingElements);
    }

    private static int getMatchingElements(String[] split) {
        List<Integer> winningNums = getCardNumbers(split[0]);
        List<Integer> numbersInCard = getCardNumbers(split[1]);

        return Utils.countMatchingElements(winningNums, numbersInCard);
    }

    private static List<Integer> getCardNumbers(String str) {
        return Arrays.stream(str.strip().split(" "))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .toList();
    }
}
