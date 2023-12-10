package main.AOC2023.Day10;

import main.AOCLineProcessor;
import main.Direction;
import main.Printable;
import main.Utils;

import java.util.*;

public class Main {

    static class Spot implements Printable {
        final char character;
        final Collection<Direction> directions;
        final int index;
        Direction enteredFromDir;

        public Spot(int index, char character) {
            this.index = index;
            this.character = character;
            this.directions = DIRECTION_MAP.get(character);
        }

        public Spot getEmptySpot() {
            return new Spot(index, EMPTY_SPOT);
        }

        public Direction getNextDir() {
            if (enteredFromDir != null) {
                List<Direction> directionList = directions.stream().filter(direction -> direction != enteredFromDir).toList();
                if (directionList.size() > 1) {
//                    throw new RuntimeException("More than 1 direction");
                }
                return directionList.get(0);
            }
            return null;
        }

//        public Direction getEnteredFromDir() {
//            return enteredFromDir;
//        }

        public void setEnteredFromDir(Direction enteredFromDir) {
            if (START_SPOT == character) {
//                System.out.println("Not setting entered from to start");
                return;
            }
            this.enteredFromDir = enteredFromDir;
        }

        public char getCharacter() {
            return character;
        }

        public boolean isEmpty() {
            return character == EMPTY_SPOT;
        }

        public boolean isStart() {
            return character == START_SPOT;
        }

        public Collection<Direction> getDirections() {
            return directions;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public String toString() {
            Character c = REPLACE_CHAR_MAP.get(character);
            return c != null ? String.valueOf(c) : " ";
        }

        @Override
        public void print() {
            if (PATH.size() < 2 || PATH.contains(this)) {
                System.out.print(this);
            } else {
                System.out.print(" ");
            }

        }
    }

    private static final char EMPTY_SPOT = '.';
    private static final char START_SPOT = 'S';
    static Map<Character, Character> REPLACE_CHAR_MAP = Map.of(
            '|', '│',
            '-', '─',
            'L', '└',
            'J', '┘',
            '7', '┐',
            'F', '┌',
            EMPTY_SPOT, ' ',
            START_SPOT, 'S'
    );
    static Map<Character, List<Direction>> DIRECTION_MAP = Map.of(
            '|', List.of(Direction.UP, Direction.DOWN),
            'L', List.of(Direction.UP, Direction.RIGHT),
            'J', List.of(Direction.UP, Direction.LEFT),
            '7', List.of(Direction.DOWN, Direction.LEFT),
            'F', List.of(Direction.RIGHT, Direction.DOWN),
            '-', List.of(Direction.RIGHT, Direction.LEFT),
            START_SPOT, List.of(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)
    );
    static LinkedList<Spot> PATH = new LinkedList<>();
    static List<Spot> MAP = new ArrayList<>();
    static Integer WIDTH;


    public static void main(String[] args) {
        new AOCLineProcessor(Main.class).getIterator().forEachRemaining(Main::printInput);
        Utils.printListWithElementsPerRow(MAP, WIDTH);
        findPath();
        System.out.println(PATH);
        Utils.printListWithElementsPerRow(MAP, WIDTH);

        System.out.println("Result: " + PATH.size() / 2);
    }

    private static void findPath() {
//        System.out.println("Path starts at index " + PATH.getFirst().getIndex());
        do {
            Spot currSpot = PATH.getLast();
            boolean addedSpot = false;
            for (Direction dir : currSpot.getDirections()) {
                Spot nextSpot = getNextSpot(currSpot.getIndex(), dir);
                if (nextSpot == null) {
                    continue;
                }
                if (PATH.contains(nextSpot) && !nextSpot.isStart()) {
//                    System.out.println("END? " + nextSpot + " , " + MAP.get(nextSpot));
                    continue;
                }
                if (canAddToPath(nextSpot, dir)) {
//                    System.out.println("Moving " + dir.name());
                    nextSpot.setEnteredFromDir(Direction.getOppositeDir(dir));
                    PATH.add(nextSpot);
//                    System.out.println(PATH);
                    addedSpot = true;
                    if (nextSpot.isStart() && PATH.size() > 3) {
//                        System.out.println("END?");
                        return;
                    }
                    break;
                }
            }
            if (!addedSpot) {
                //Go back
//                System.out.println("going back");
                MAP.replaceAll(spot -> {
                    if (!spot.isStart() && spot.equals(PATH.getLast())) {
                        return spot.getEmptySpot();
                    } else {
                        return spot;
                    }
                });
                PATH.removeLast();
            }

//            System.out.println("Next possible directions: " + getNextIndexes(startIndex, MAP.get(startIndex)));
        } while (PATH.size() > 1 || PATH.getLast().isStart());
    }

    private static boolean canAddToPath(Spot nextSpot, Direction dir) {
        //If empty, can't connect
        if (nextSpot.isEmpty()) {
            return false;
        }
        Spot currSpot = PATH.getLast();
        Direction nextDir = currSpot.getNextDir();
        if (nextDir == null) {
            return nextSpot.getDirections().contains(Direction.getOppositeDir(dir));
        } else {
            return nextDir.equals(dir);
        }
    }

    private static List<Integer> getNextIndexes(int index, Character currChar) {
        return getNextIndexes(index, DIRECTION_MAP.get(currChar));
    }

    private static Spot getNextSpot(int index, Direction dir) {
        List<Integer> nextIndexes = getNextIndexes(index, List.of(dir));
        if (nextIndexes.size() > 1) {
            throw new RuntimeException("More than 1 next indexes");
        } else if (nextIndexes.size() == 1) {
            return MAP.get(nextIndexes.get(0));
        }
        return null;

    }

    private static List<Integer> getNextIndexes(int index, Collection<Direction> directions) {
        return Utils.indexesAround(MAP, WIDTH, index, directions);
    }

    private static void printInput(String line) {

        if (WIDTH == null) {
            WIDTH = line.length();
        }

        for (Character c : line.toCharArray()) {
            int index = MAP.size() != 0 ? MAP.size() : 0;
            Spot spot = new Spot(index, c);
            MAP.add(spot);
            if (c.equals(START_SPOT)) {
                PATH.add(spot);
            }
        }
    }
}
