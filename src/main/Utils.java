package main;

import java.util.ArrayList;
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

    public static void printListWithElementsPerRow(List<? extends Printable> list, int elementsPerRow) {
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
}
