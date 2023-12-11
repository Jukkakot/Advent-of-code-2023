package main;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {
    public static List<Integer> indexesAround(List<?> list, int width, int index) {
        int rows = list.size() / width;
        int cols = width;

        int row = index / width;
        int col = index % width;

        List<Integer> result = new ArrayList<>();

        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if ((i >= 0 && i < rows) && (j >= 0 && j < cols) && !(i == row && j == col)) {
                    // Ensure not to wrap around edges
                    if ((i == row - 1 || i == row + 1 || j == col - 1 || j == col + 1) ||
                            (i == row && j == col)) {
                        int newIndex = i * width + j;
                        result.add(newIndex);
                    }
                }
            }
        }

        return result;
    }

    public static List<Integer> indexesAround(List<?> list, int width, int index, Collection<Direction> directions) {
        int rows = list.size() / width;
        int cols = width;

        int row = index / width;
        int col = index % width;

        List<Integer> result = new ArrayList<>();

        for (Direction direction : directions) {
            switch (direction) {
                case UP:
                    if (row - 1 >= 0) {
                        result.add((row - 1) * width + col);
                    }

                    break;
                case DOWN:
                    if (row + 1 < rows) {
                        result.add((row + 1) * width + col);
                    }
                    break;
                case LEFT:
                    if (col - 1 >= 0) {
                        result.add(row * width + (col - 1));
                    }
                    break;
                case RIGHT:
                    if (col + 1 < cols) {
                        result.add(row * width + (col + 1));
                    }
                    break;
                default:
                    throw new InvalidParameterException(String.format("Invalid direction %s", directions));
            }
        }

        return result;
    }

    public static void printPrintableListWithElementsPerRow(List<? extends Printable> list, int elementsPerRow) {
        int size = list.size();
        int printedElements = 0;

        for (Printable item : list) {
            item.print();
            printedElements++;

            if (printedElements % elementsPerRow == 0 || printedElements == size) {
                System.out.println();
            }
        }
    }
    public static void printListWithElementsPerRow(List<?> list, int elementsPerRow) {
        int size = list.size();
        int printedElements = 0;

        for (Object item : list) {
            System.out.print(item);
            printedElements++;

            if (printedElements % elementsPerRow == 0 || printedElements == size) {
                System.out.println();
            }
        }
    }
    public static boolean rangesContainEachOther(int[] range1, int[] range2) {
        if ((range1[0] <= range2[0] && range1[1] >= range2[1])
                || (range2[0] <= range1[0] && range2[1] >= range1[1])) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean rangesOverlap(int[] range1, int[] range2) {
        if (range1[0] <= range2[1] && range1[1] >= range2[0]) {
            return true; // Ranges overlap
        } else {
            return false; // Ranges do not overlap
        }
    }

    public static <T> int countMatchingElements(List<T> list1, List<T> list2) {
        int count = 0;

        for (T element : list1) {
            if (list2.contains(element)) {
                count++;
            }
        }

        return count;
    }

    public static int[] get2DCoordinates(int index, int rowLength) {
        int[] coordinates = new int[2];
        if (index < 0) {
            // Handle invalid index
            System.out.println("Invalid index");
            return coordinates;
        }

        coordinates[0] = index % rowLength; // Calculate the column
        coordinates[1] = index / rowLength; // Calculate the row

        return coordinates;
    }

    public static int shortestPath(int[] pointA, int[] pointB) {
        int rowDifference = Math.abs(pointB[0] - pointA[0]);
        int columnDifference = Math.abs(pointB[1] - pointA[1]);

        // The total Manhattan distance is the minimum steps required
        return rowDifference + columnDifference;
    }

    public static <T> List<List<T>> getUniquePairs(List<T> list) {
        List<List<T>> pairs = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                List<T> pair = new ArrayList<>();
                pair.add(list.get(i));
                pair.add(list.get(j));
                pairs.add(pair);
            }
        }

        return pairs;
    }

    public static <T> List<List<T>> createListOfLists(List<T> list, int count) {
        List<List<T>> listOfLists = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            // Create a new list and add elements from the given list to it
            List<T> newList = new ArrayList<>(list);
            listOfLists.add(newList);
        }

        return listOfLists;
    }
    public static boolean arraysMatch(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
