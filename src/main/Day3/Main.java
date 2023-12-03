package main.Day3;

import main.AOCLineProcessor;
import main.Printable;
import main.Utils;

import java.util.ArrayList;
import java.util.List;

//results tried:
//510975
//507971
//512224

public class Main {
    private static class Spot implements Printable {
        private final Object value;
        private boolean isCounted = false;
        private boolean isPrinted = false;

        public Spot(Object value) {
            this.value = value;
        }

        public Spot(Character value) {
            this.value = String.valueOf(value);
        }

        public Object getValue() {
            return value;
        }

        public boolean isSymbol() {
            return !".".equals(value) && getInt((String) value) == null;
        }

        public boolean isCounted() {
            return isCounted;
        }

        public void markAsCounted() {
            isCounted = true;
        }

        @Override
        public String toString() {
            if (isCounted) {
                return "C";
//                return "(" + value.toString() + ")";
            } else if (isSymbol()) {
                return value.toString();
            } else if (!".".equals(value)) {
                return value.toString();
//                    return "N";
            }
            return value.toString();
//            return value != null && !"".equals(value) && !".".equals(value) ? value.toString() : ".";
        }

        @Override
        public void print() {
            if (!isCounted && isPrinted) {
                return;
            }
            isPrinted = true;
            System.out.print(this);
        }
    }

    static List<Spot> SPOTS = new ArrayList<>();
    static int WIDTH = 0;
    static boolean USE_NEGATIVE_NUMBERS = true;
    //    static List<Character> VALID_CHARACTERS = List.of('-', '+');
    static List<Character> VALID_CHARACTERS = new ArrayList<>();

    public static void main(String[] args) {
        int finalResult = 0;
        new AOCLineProcessor(Main.class).getIterator().forEachRemaining(Main::doYourThing);
//        System.out.println("spots " + SPOTS.size() + " " + SPOTS);
        if (SPOTS.size() != WIDTH * WIDTH) {
            System.err.println("Error, spots size is wrong");
        }
        for (int i = 0; i < SPOTS.size(); i++) {
            Spot spot = SPOTS.get(i);
            if (spot.isSymbol()) {
//                System.out.println("symbol " + spot + "index " + i);
                int value = 0;
                List<Integer> foundNumbers = new ArrayList<>();
                for (int index : Utils.indexesAround(SPOTS, WIDTH, i)) {
                    Spot aroundSpot = SPOTS.get(index);

                    Integer anInt = getInt(String.valueOf(aroundSpot.getValue()));
                    if (anInt != null && !aroundSpot.isCounted()) {
//                        System.out.println("Value " + SPOTS.get(index) + " around symbol " + spot + " index " + i);
                        aroundSpot.markAsCounted();
                        foundNumbers.add(anInt);
                    }
                }
                if (foundNumbers.size() == 2) {
                    finalResult += foundNumbers.get(0) * foundNumbers.get(1);
                }

            }
        }
        Utils.printListWithElementsPerRow(SPOTS, WIDTH);

//        for (int i = 0; i < SPOTS.size(); i++) {
//            Spot spot = SPOTS.get(i);
//            spot.print();
//            if (!spot.isSymbol() && !".".equals(String.valueOf(spot.getValue()))) {
//                int length = ((String) spot.getValue()).length();
//                if (i % WIDTH != 0 && WIDTH - (i % WIDTH) > length - 1) {
//                    i += length - 1;
//                }
////                else {
////                    i += i % WIDTH;
////                }
//            }
//            if (i != 0 && i % WIDTH == 0) {
//                System.out.println();
//            }
//        }
        System.out.println();
        System.out.println("Result: " + finalResult);
    }

    private static void doYourThing(String line) {
        WIDTH = line.length();
        List<String> split = new ArrayList<>(List.of(line.split("((?=\\.)|(?<=\\.))", -1)));
        List<Spot> spots = new ArrayList<>();
        for (String value : split) {
            if (getInt(value) != null && getInt(value.substring(0, 1)) != null) {
                //Number
                addValues(spots, value);
            } else if (value.length() > 1) {
                //Number + symbol + number
                //Symbol can be at the start of number or at the end or both.
                char firstChar = value.charAt(0);
                char lastChar = value.charAt(value.length() - 1);

                boolean firstCharIsSymbol = !VALID_CHARACTERS.contains(firstChar) && getInt(firstChar) == null;
                if (firstCharIsSymbol) {
                    //first char is symbol
                    spots.add(new Spot(firstChar));
                }
                boolean lastCharIsSymbol = getInt(lastChar) == null;
                if (lastCharIsSymbol) {
                    //last char is symbol
                    addValues(spots, value.substring(firstCharIsSymbol ? 1 : 0, value.length() - 1));
                    spots.add(new Spot(lastChar));
                } else if (firstCharIsSymbol) {
                    addValues(spots, value.substring(1));
                }
                if (!firstCharIsSymbol && !lastCharIsSymbol) {
                    for (int i = 1; i < value.length(); i++) {
                        Integer parsedInt = getInt(value.substring(0, i));
                        if (parsedInt == null) {
                            //First number
                            addValues(spots, value.substring(0, i - 1));
                            //now assuming only one symbol next and then another number.. :D
                            spots.add(new Spot(value.charAt(i - 1)));
                            addValues(spots, value.substring(i));
                            break;
                        }
                    }


                }
//                if(firstWasSymbol &&)
            } else if (!"".equals(value)) {
                //Empty or symbol
                spots.add(new Spot(value));
            }
        }
//        System.out.println(line);
//        System.out.println(spots);
//        System.out.println("splits " + split.size() + " " + split);
//        System.out.println("spots " + spots.size() + " " + spots);
//        System.out.println("lines " + line.length() + " " + line);
//        System.out.println();
        SPOTS.addAll(spots);
//        if (spots.size() != WIDTH) {
//            System.out.println("splits " + split.size() + " " + split);
//            System.out.println("spots " + spots.size() + " " + spots);
//            System.out.println("lines " + line.length() + " " + line);
//            System.out.println();
//        }
    }

    private static void addValues(List<Spot> spots, String value) {
        if (getInt(value) == null) {
            throw new NumberFormatException("Invalid number dude " + value);
        }
        Spot spot = new Spot(value);
        for (int j = 0; j < value.length(); j++) {
            spots.add(spot);
        }
    }

    private static Integer getInt(Character character) {
        return getInt(String.valueOf(character));
    }

    private static Integer getInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ignored) {
        }
        return null;
    }
}
